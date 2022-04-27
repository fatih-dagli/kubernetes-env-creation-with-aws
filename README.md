# Kubernetes deployment with ansible, terraform and jenkins

I used the AWS Cloud Environment for this scenario. You can follow below steps to generate kubernetes cluster.


# Prerequisites


1. You should create new AWS account and new AWS instance using Ubuntu 20.04 image.


2. You should create key pair to connect EC2 instance via SSH. And download key to local.

      ![Key_pair_create](https://github.com/fatih-dagli/kubernetes-env-creation-with-aws/blob/main/images/create_key_pair.png)


3. Create new IAM role with **AmazonEC2FullAccess** and **AmazonEC2ContainerRegistryFullAccess** Policy using Create role button. And assign this AIM role to EC2 instance.

      ![create_iam_role](https://github.com/fatih-dagli/kubernetes-env-creation-with-aws/blob/main/images/create_iam_role.png)

      ![assign_iam_role](https://github.com/fatih-dagli/kubernetes-env-creation-with-aws/blob/main/images/assign_iam_role.png)

      ![assign_iam_role_2](https://github.com/fatih-dagli/kubernetes-env-creation-with-aws/blob/main/images/assign_iam_role_2.png)



4. Add some related port to security group

      ![security_group_port](https://github.com/fatih-dagli/kubernetes-env-creation-with-aws/blob/main/images/security_group_port.png)


5. Create private ECR repository image for docker images using below URL

      https://us-east-1.console.aws.amazon.com/ecr/create-repository?publicRepoCreate=true&region=us-east-1

      ![ECR_CREEATE](https://github.com/fatih-dagli/kubernetes-env-creation-with-aws/blob/main/images/ECR_CREEATE.png)


6. Get secret and access key and enter it in ansible prompt

      ![ACCESS_KEY](https://github.com/fatih-dagli/kubernetes-env-creation-with-aws/blob/main/images/ACCESS_KEY.png)


7. Install Ansible in machine which you created manually using below guide

      https://github.com/fatih-dagli/kubernetes-env-creation-with-aws/blob/main/images/ansible_installation.md


8. Install Terraform in machine which you created manually using below guide

      https://github.com/fatih-dagli/kubernetes-env-creation-with-aws/blob/main/images/terraform_installation.md
      
 
 
 
 
# Installation Steps
 
Now you are ready to execute below two command to install your kubernetes cluster !

Second command will ask your AWS access adn secret key,  you should enter this parameters.

```
ansible-playbook --tags install-terraform project.yml -k
ansible-playbook --tags "prepare-k8s-all, initialize-k8s-master, initialize-k8s-node, install-jenkins" project.yml -k
```
