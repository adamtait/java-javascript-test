


#
# AWS provider

provider "aws" {
   profile   = "6Pages"
   region    = "us-east-1"
}



#
# local vars

locals {
  environment            = "dev"
}



#
# graphql api

module "java_javascript_test_api" {
  source         = "../../lambda"
  
  environment    = local.environment
  name           = "java-javascript-test-api-${local.environment}"
  description    = "[${local.environment}] generates text from json."
}