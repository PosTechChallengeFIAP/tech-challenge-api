output "api_public_ip" {
  description = "ECS public IP"
  value       = aws_instance.ecs_instance.public_ip
}

output "api_instance_id" {
  description = "ECS instance ID"
  value       = aws_instance.ecs_instance.id
}