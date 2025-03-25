resource "aws_ecs_cluster" "ecs_cluster" {
  name = "tech-challenge-api-ecs-cluster"
}

resource "aws_iam_instance_profile" "ecs_instance_profile" {
  name = "ecs-instance-profile"
  role = "LabRole"
}

resource "aws_instance" "ecs_instance" {
  ami                         = "ami-001338ee8479f6dc1"
  instance_type               = "t3.medium"
  subnet_id                   = data.terraform_remote_state.network.outputs.api_public_subnet_id
  vpc_security_group_ids      = [data.terraform_remote_state.network.outputs.api_sg_id]
  associate_public_ip_address = true
  iam_instance_profile        = aws_iam_instance_profile.ecs_instance_profile.name
  key_name                    = "tech-challenge-api-ecs-instance-key"

  user_data = <<-EOF
              #!/bin/bash
              set -ex

              yum update -y
              yum install -y ecs-init

              echo "ECS_CLUSTER=${aws_ecs_cluster.ecs_cluster.name}" >> /etc/ecs/ecs.config
              echo "ECS_BACKEND_HOST=" >> /etc/ecs/ecs.config
              
              systemctl enable --now ecs
  EOF

  tags = {
    Name = "tech-challenge-ec2-cluster"
  }
}

resource "aws_ecs_task_definition" "app_task" {
  family                   = "tech-challenge-api-ecs-task"
  network_mode             = "awsvpc"
  requires_compatibilities = ["EC2"]

  container_definitions = jsonencode([
    {
      name      = "tech-challenge-app"
      image     = "loadinggreg/tech-challenge:${var.tc_image_tag}"
      cpu       = 256
      memory    = 512
      essential = true

      healthCheck = {
        command = ["CMD-SHELL", "curl -f http://localhost:8080/health || exit 1"]
        interval = 30
        retries  = 5
        start_period = 60
        timeout = 5
      }

      portMappings = [
        {
          containerPort = 8080
          hostPort      = 8080
          protocol      = "tcp"
        }
      ]

      logConfiguration = {
        logDriver = "awslogs"
        options = {
          "awslogs-group"         = "/ecs/tech-challenge-api"
          "awslogs-region"        = "us-west-2"
          "awslogs-stream-prefix" = "app-ecs"
        }
      }
      
      environment = [
        {
          name  = "DB_HOST"
          value = aws_rds_cluster.aurora_cluster.endpoint
        },
        {
          name  = "DB_PORT"
          value = "3306"
        },
        {
          name  = "DB_USER"
          value = var.db_username
        },
        {
          name  = "DB_PASSWORD"
          value = var.db_password
        },
        {
          name  = "DB_NAME"
          value = var.db_name
        },
        {
          name = "BACK_URL_MERCADO_PAGO",
          value = aws_apigatewayv2_api.ecs_api.api_endpoint
        },
        {
          name  = "KEY_MERCADO_PAGO"
          value = var.key_mercado_pago
        },
        {
          name  = "COGNITO_API_URL"
          value = "https://tech-challenge-severino.us-west-2.amazonaws.com/"
        }
      ]
    }
  ])
}

resource "aws_ecs_service" "app_service" {
  name                    = "tech-challenge-api-ecs-service"
  cluster                 = aws_ecs_cluster.ecs_cluster.id
  task_definition         = aws_ecs_task_definition.app_task.arn
  desired_count           = 2
  launch_type             = "EC2"
  force_new_deployment    = true

  network_configuration {
    subnets          = [aws_subnet.private.id]
    security_groups  = [data.terraform_remote_state.network.outputs.api_sg_id]
    assign_public_ip = false
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.ecs_target_group.arn
    container_name   = "tech-challenge-app"
    container_port   = 8080
  }

  deployment_controller {
    type = "ECS"
  }

  depends_on = [aws_rds_cluster.aurora_cluster, aws_rds_cluster_instance.aurora_instance]
}

resource "aws_cloudwatch_log_group" "ecs_log_group" {
  name = "/ecs/tech-challenge-api"
}
