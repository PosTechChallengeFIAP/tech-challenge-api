resource "aws_eip" "api_eip" {
  domain = "vpc"

  tags = {
    Name = "tech_challenge_api_eip"
  }
}

resource "aws_eip_association" "api_eip_association" {
  instance_id   = aws_instance.ecs_instance.id
  allocation_id = aws_eip.api_eip.id
}