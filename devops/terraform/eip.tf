resource "aws_eip" "ecs_nat_eip" {
  domain   = "vpc"
  tags = {
    Name = "tech-challenge-api-ecs-instance-eip"
  }
}

resource "aws_nat_gateway" "ecs_nat_gateway" {
  allocation_id = aws_eip.ecs_nat_eip.id
  subnet_id     = data.terraform_remote_state.network.outputs.api_public_subnet_id

  tags = {
    Name = "tech-challenge-nat-gateway"
  }
}

resource "aws_route" "private_subnet_nat_route" {
  route_table_id         = aws_route_table.private_route_table.id
  destination_cidr_block = "0.0.0.0/0"
  nat_gateway_id         = aws_nat_gateway.ecs_nat_gateway.id
}

resource "aws_route_table" "private_route_table" {
  vpc_id = data.terraform_remote_state.network.outputs.main_vpc_id

  tags = {
    Name = "private-route-table"
  }
}