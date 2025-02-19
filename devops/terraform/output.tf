output "ecs_instance_public_ip" {
  description = "IP público da instância ECS"
  value       = aws_instance.ecs_instance.public_ip
}