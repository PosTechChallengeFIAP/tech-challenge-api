resource "aws_cognito_user_pool" "app_user_pool" {
  name = "tech-challenge-user-pool"

  auto_verified_attributes = ["email"]
  username_attributes = ["cpf"]

  schema {
    name                     = "name"
    attribute_data_type      = "String"
    required                 = true
    mutable                  = true
  }

  schema {
    name                     = "custom:cpf"
    attribute_data_type      = "String"
    mutable                  = false
    required                 = true
    string_attribute_constraints {
      min_length = 11
      max_length = 11
    }
  }

  password_policy {
    minimum_length                   = 8
    require_lowercase                = true
    require_uppercase                = true
    require_numbers                  = true
    require_symbols                  = true
  }

  admin_create_user_config {
    allow_admin_create_user_only = false
  }
}

resource "aws_cognito_user_pool_client" "app_user_pool_client" {
  name         = "tech-challenge-client"
  user_pool_id = aws_cognito_user_pool.app_user_pool.id

  explicit_auth_flows = [
    "ALLOW_USER_PASSWORD_AUTH",
    "ALLOW_REFRESH_TOKEN_AUTH",
    "ALLOW_USER_SRP_AUTH"
  ]

  generate_secret = false
}

resource "aws_cognito_user_pool_domain" "my_domain" {
  domain       = "tech-challenge-severino"
  user_pool_id = aws_cognito_user_pool.app_user_pool.id
}