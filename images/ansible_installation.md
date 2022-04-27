1.	Execute below commands to install ansible
```
sudo apt update
sudo apt install ansible
```

2.	Add below line to /etc/ansible/hosts using vim /etc/ansible/hosts
```
terraform ansible_host=172.31.27.69 ansible_user=root
```

3.	Change root user ssh permission and change root password
```
sudo su
sed -i "s/#PermitRootLogin prohibit-password/PermitRootLogin yes/g" /etc/ssh/sshd_config
systemctl restart sshd
passwd root
```

4.	change PasswordAuthentication no >>> PasswordAuthentication yes in /etc/ssh/sshd_config
```
sed -i "s/PasswordAuthentication no/PasswordAuthentication yes/g" /etc/ssh/sshd_config
```

5.	Install sshpass, docker and awscli package
```
apt-get install sshpass
apt  install docker.io
apt-get install awscli
```

6.	Change ansible host file path in /etc/ansible/ansible.cfg
vim /etc/ansible/ansible.cfg
```
OLD:     #inventory      = /etc/ansible/hosts  
NEW:    inventory      = /home/ubuntu/kubernetes-env-creation-with-aws/ansible/hosts
```

7.	Change host_key_checking  parameter in /etc/ansible/ansible.cfg  
vim /etc/ansible/ansible.cfg
```
OLD:     #host_key_checking = False
NEW:    host_key_checking = False
```

8.	Install terraform plugin
```
ansible-galaxy collection install community.general
```

9.	Install some required python package for jenkins job creation
```
apt install python3-pip
pip install docker
pip install python-jenkins
```
