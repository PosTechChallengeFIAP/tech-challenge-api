resource "aws_cognito_user_pool" "app_user_pool" {
  name = "tech-challenge-user-pool"

  auto_verified_attributes = ["email"]
  alias_attributes = ["preferred_username"]

  schema {
    name                     = "preferred_username"
    attribute_data_type      = "String"
    required                 = true
    mutable                  = false
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

resource "aws_cognito_user_pool_domain" "my_domain" {
  domain       = "tech-challenge-severino-d41d8"
  user_pool_id = aws_cognito_user_pool.app_user_pool.id
}