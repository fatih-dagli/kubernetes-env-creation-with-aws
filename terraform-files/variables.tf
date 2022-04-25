variable "aws_region" {
       description = "The AWS region to create things in." 
       default     = "us-east-1" 
}

variable "key_name" { 
    description = " SSH keys to connect to ec2 instance" 
    default     =  "terraform" 
}

variable "instance_type" { 
    description = "instance type for ec2" 
    default     =  "t2.micro" 
}

variable "security_group" { 
    description = "Name of security group" 
    default     = "launch-wizard-1" 
}

variable "tag_name" { 
    description = "Tag Name of for Ec2 instance" 
    default     = "my-cluster-instance" 
} 

variable "instance_tags" {
  type = list
  default = ["k8s-master", "k8s-node"]
}

variable "ami_id" { 
    description = "AMI for Ubuntu Ec2 instance" 
    default     = "ami-04505e74c0741db8d" 
}

variable "availability_zone" {
     type = string
     default = "us-east-1d"
}

variable "instance_count" {
     default = "2"
}

variable "project_path" {
     default = "/home/ubuntu"
}