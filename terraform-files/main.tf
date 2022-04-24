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
  size                  = 10
}

# Attach EBS Volume
resource "aws_volume_attachment" "volume_attachment" {
  count                 = "${var.instance_count}"
  device_name           = "/dev/sdh"
  volume_id             = "${aws_ebs_volume.ebs_volume.*.id[count.index]}"
  instance_id           = "${aws_instance.myClusterInstance.*.id[count.index]}"
}

resource "null_resource" "local-command-1" {
  triggers = {
    cluster_instance_ids = "${join(",", aws_instance.myClusterInstance.*.id)}"
  }

  provisioner "local-exec" {
    command = "echo 'k8s-master ansible_host=${aws_instance.myClusterInstance.0.private_ip} ansible_user=root' >> /home/ubuntu/ansible/hosts "
  }
}


resource "null_resource" "local-command-2" {
  triggers = {
    cluster_instance_ids = "${join(",", aws_instance.myClusterInstance.*.id)}"
  }

  provisioner "local-exec" {
    command = "echo 'k8s-node ansible_host=${aws_instance.myClusterInstance.1.private_ip} ansible_user=root' >> /home/ubuntu/ansible/hosts "
  }
}


resource "null_resource" "remote-command-1" {
  count           = var.instance_count

  triggers = {
    cluster_instance_ids = "${join(",", aws_instance.myClusterInstance.*.id)}"
  }

  connection {
    host = "${element(aws_instance.myClusterInstance.*.public_ip, count.index)}"
    agent       = "false"
    type        = "ssh"
    user        = "ubuntu"
    private_key = file(pathexpand("/home/ubuntu/terraform-files/terraform.pem"))
  }

  provisioner "remote-exec" {
      inline = [
        "sudo sed -i \"s/#PermitRootLogin prohibit-password/PermitRootLogin yes/g\" /etc/ssh/sshd_config",
        "sudo sed -i \"s/PasswordAuthentication no/PasswordAuthentication yes/g\" /etc/ssh/sshd_config",
        "sudo systemctl restart sshd",
        "echo 'root:Operation123' | sudo chpasswd"
      ]
  }
}
