1.	Execute below commands to install ansible
```
sudo apt update
sudo apt install ansible
```

2.	Change root user ssh permission and change root password
```
sudo su
sed -i "s/#PermitRootLogin prohibit-password/PermitRootLogin yes/g" /etc/ssh/sshd_config
systemctl restart sshd
passwd root
```

3.	change PasswordAuthentication no >>> PasswordAuthentication yes in /etc/ssh/sshd_config
```
sed -i "s/PasswordAuthentication no/PasswordAuthentication yes/g" /etc/ssh/sshd_config
```

4.	Install sshpass, docker and awscli package
```
apt-get install sshpass
apt-get install docker.io
apt-get install awscli
```

5.	Change ansible host file path (this hosts file will be generated after you cloned the repo with the name kubernetes-env-creation-with-aws/ansible/hosts) in /etc/ansible/ansible.cfg
vim /etc/ansible/ansible.cfg
```
OLD:     #inventory      = /etc/ansible/hosts  
NEW:    inventory      = /home/ubuntu/kubernetes-env-creation-with-aws/ansible/hosts
```

6.	Change host_key_checking  parameter in /etc/ansible/ansible.cfg  
vim /etc/ansible/ansible.cfg
```
OLD:     #host_key_checking = False
NEW:    host_key_checking = False
```

7.	Install terraform plugin
```
ansible-galaxy collection install community.general
```

8.	Install some required python package for jenkins job creation
```
apt install python3-pip
pip install docker
pip install python-jenkins
```