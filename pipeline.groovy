node {
    stage('Clone Project') {
          git branch: "main",
              url: 'https://github.com/fatih-dagli/kubernetes-env-creation-with-aws.git'
    }

    stage('Build Project') {
        dir("/var/lib/jenkins/workspace/initial-deploy/kubernetes") {
            sh "aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 553882164358.dkr.ecr.us-east-1.amazonaws.com"
            sh "docker build -t python:built ."
            sh "docker tag python:built 553882164358.dkr.ecr.us-east-1.amazonaws.com/python:latest"
            sh "docker push 553882164358.dkr.ecr.us-east-1.amazonaws.com/python:latest"
        }
    }

    stage('send yaml files ') {
        dir("/var/lib/jenkins/workspace/initial-deploy/kubernetes") {
            sh "sshpass -p 'Operation123' scp -o StrictHostKeyChecking=no mysql.yml root@xxx.xxx.xxx.xxx:/ "
            sh "sshpass -p 'Operation123' scp -o StrictHostKeyChecking=no python.yml root@xxx.xxx.xxx.xxx:/ "
        }
    }

    stage('Build mysql') {
        dir("/var/lib/jenkins/workspace/initial-deploy/kubernetes") {
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@xxx.xxx.xxx.xxx 'kubectl apply -f /mysql.yml'"
        }
    }

    stage('set aws_access_key_id') {
        dir("/var/lib/jenkins/workspace/initial-deploy/kubernetes") {
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@xxx.xxx.xxx.xxx 'aws configure set aws_access_key_id AKIAYB5PTDSDCC35M3V6'"
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@yyy.yyy.yyy.yyy 'aws configure set aws_access_key_id AKIAYB5PTDSDCC35M3V6'"
        }
    }

    stage('set aws_secret_access_key') {
        dir("/var/lib/jenkins/workspace/initial-deploy/kubernetes") {
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@xxx.xxx.xxx.xxx 'aws configure set aws_secret_access_key NnGo6leNdzoN/LYG6c39itTBAuDF0+750dAvqtql'"
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@yyy.yyy.yyy.yyy 'aws configure set aws_secret_access_key NnGo6leNdzoN/LYG6c39itTBAuDF0+750dAvqtql'"
        }
    }

    stage('login to aws registry') {
        dir("/var/lib/jenkins/workspace/initial-deploy/kubernetes") {
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@xxx.xxx.xxx.xxx 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 553882164358.dkr.ecr.us-east-1.amazonaws.com'"
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@yyy.yyy.yyy.yyy 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 553882164358.dkr.ecr.us-east-1.amazonaws.com'"
        }
    }
    
    stage('Build python') {
        dir("/var/lib/jenkins/workspace/initial-deploy/kubernetes") {
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@xxx.xxx.xxx.xxx 'kubectl create secret docker-registry regcred --docker-server=553882164358.dkr.ecr.us-east-1.amazonaws.com --docker-username=AWS --docker-password=\$(aws ecr get-login-password --region us-east-1)'"
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@xxx.xxx.xxx.xxx 'kubectl apply -f /python.yml'"
        }
    }
}