provider "aws" {
  region = var.aws_region
}

#Create security group with firewall rules
#resource "aws_security_group" "security_jenkins_grp" {
#  name        = var.security_group
#  description = "security group for jenkins"
#
#  ingress {
#    from_port   = 8080
#    to_port     = 8080
#    protocol    = "tcp"
#    cidr_blocks = ["0.0.0.0/0"]
#  }
#
# ingress {
#    from_port   = 22
#    to_port     = 22
#    protocol    = "tcp"
#    cidr_blocks = ["0.0.0.0/0"]
#  }
#
# # outbound from jenkis server
#  egress {
#    from_port   = 0
#    to_port     = 65535
#    protocol    = "tcp"
#    cidr_blocks = ["0.0.0.0/0"]
#  }
#
#  tags= {
#    Name = var.security_group
#  }
#}


# Create Elastic IP address
#resource "aws_eip" "myElasticIP" {
#  vpc      = true
#  instance = aws_instance.myFirstInstance.id
#tags= {
#    Name = "elastic_ip"
#  }
#}


resource "aws_instance" "myClusterInstance" {
  count           = var.instance_count
  ami             = var.ami_id
  key_name        = var.key_name
  instance_type   = var.instance_type
  security_groups = [var.security_group]
  tags = {
    Name  = element(var.instance_tags, count.index)
  }
  root_block_device {
    volume_size           = 30
  }
}

# Create EBS volume
resource "aws_ebs_volume" "ebs_volume" {
  count                 = "${var.instance_count}"
  availability_zone     = var.availability_zone
  size                  = 30
}

# Attach EBS Volume
resource "aws_volume_attachment" "volume_attachment" {
  count                 = "${var.instance_count}"
  device_name           = "/dev/sdh"
  volume_id             = "${aws_ebs_volume.ebs_volume.*.id[count.index]}"
  instance_id           = "${aws_instance.myClusterInstance.*.id[count.index]}"
}