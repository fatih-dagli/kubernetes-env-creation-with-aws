Execute below commands to install terraform

````
sudo mkdir -p /opt/terraform
cd /opt/terraform
sudo wget https://releases.hashicorp.com/terraform/1.1.7/terraform_1.1.7_linux_386.zip
sudo apt-get install unzip -y
sudo unzip terraform_1.1.7_linux_386.zip
sudo mv /opt/terraform/terraform /usr/bin/
terraform --version
```
