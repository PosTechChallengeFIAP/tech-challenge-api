variable "db_name" {
  description = "The name of the database"
  type        = string
}

variable "db_username" {
  description = "The username for the database"
  type        = string
}

variable "db_password" {
  description = "The password for the database"
  type        = string
}

variable "key_mercado_pago" {
  description = "Key Mercado Pago"
  type        = string
  sensitive   = true
}

variable "tc_image_tag" {
  description = "Tag for the docker image"
  type        = string
}