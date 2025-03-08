resource "aws_rds_cluster" "aurora_cluster" {
  cluster_identifier = "tech-challenge-aurora"
  engine             = "aurora-mysql"
  engine_version     = "8.0.mysql_aurora.3.04.0"
  database_name      = var.db_name
  master_username    = var.db_username
  master_password    = var.db_password
  skip_final_snapshot = true

  db_subnet_group_name = data.terraform_remote_state.network.outputs.aurora_subnet_group_name
  vpc_security_group_ids = [data.terraform_remote_state.network.outputs.aurora_sg_id]

  storage_encrypted = true
  backup_retention_period = 7

  tags = {
    Name = "tech-challenge-aurora-cluster"
  }
}

resource "aws_rds_cluster_instance" "aurora_instance" {
  identifier         = "tech-challenge-aurora-instance"
  cluster_identifier = aws_rds_cluster.aurora_cluster.id
  instance_class     = "db.t3.medium"
  engine             = aws_rds_cluster.aurora_cluster.engine

  tags = {
    Name = "tech-challenge-aurora-instance"
  }
}
