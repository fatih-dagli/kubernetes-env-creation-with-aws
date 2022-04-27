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
      
9. Go any folder in machine and clone this repository.
      ```
      git clone https://github.com/fatih-dagli/kubernetes-env-creation-with-aws.git
      ```

10. You should configure your personal informations and path in 

      - terraform/variable.tf (Change AWS account information)
      - ansible/project.yml   (Change project paths)
      - ansbile/hosts         (Change IP address with your machine IP address)
      
      
   



# Installation Steps
 
Now you are ready to execute below two commands to install your kubernetes cluster !


Second command will ask your AWS access, secret key, AWS region and AWS account id.  You should enter these parameters. 

Execute below commands in **kubernetes-env-creation-with-aws/ansible** folder

```
ansible-playbook --tags install-terraform project.yml -k
ansible-playbook --tags "prepare-k8s-all, initialize-k8s-master, initialize-k8s-node, install-jenkins" project.yml -k
```


Also If you want to deploy/update again your deployment after first installation,  you can enter to jenkins and trigger pipeline manually. Jenkins and Application information will be shown in ansible output after installation finished successfully.
