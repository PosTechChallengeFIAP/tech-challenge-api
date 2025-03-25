resource "aws_subnet" "private" {
  vpc_id                  = data.terraform_remote_state.network.outputs.main_vpc_id
  cidr_block              = "10.0.6.0/24"
  availability_zone       = "us-west-2a"
  map_public_ip_on_launch = false

  tags = {
    Name = "tech-challenge-ecs-private-subnet"
  }
}

resource "aws_eip" "nat" {
    vpc = true
}

resource "aws_nat_gateway" "nat" {
  allocation_id = aws_eip.nat.id
  subnet_id     = data.terraform_remote_state.network.outputs.api_public_subnet_id

  tags = {
    Name = "tech-challenge-ecs-nat-gateway"
  }
}

resource "aws_route_table" "private" {
  vpc_id = data.terraform_remote_state.network.outputs.main_vpc_id

  route {
    cidr_block = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.nat.id
  }

  tags = {
    Name = "tech-challenge-api-private-route-table"
  }
}

resource "aws_route_table_association" "private" {
  subnet_id      = aws_subnet.private.id
  route_table_id = aws_route_table.private.id
}