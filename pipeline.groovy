node {
    stage('Clone Project') {
          git branch: "main",
              url: 'https://github.com/fatih-dagli/kubernetes-env-creation-with-aws.git'
    }

    stage('Build Project') {
        dir("/var/lib/jenkins/workspace/initial-deploy/kubernetes") {
            sh "aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 553882164358.dkr.ecr.us-east-1.amazonaws.com"
            sh "docker build -t python:built ."
            sh "docker tag 553882164358.dkr.ecr.us-east-1.amazonaws.com/python:latest"
            sh "docker push 553882164358.dkr.ecr.us-east-1.amazonaws.com/python:latest"
        }
    }

    stage('send yaml files ') {
        dir("/var/lib/jenkins/workspace/initial-deploy/kubernetes") {
            sh "sshpass -p 'Operation123' scp mysql.yml -o StrictHostKeyChecking=no root@xxx.xxx.xxx.xxx:/ "
            sh "sshpass -p 'Operation123' scp python.yml -o StrictHostKeyChecking=no root@xxx.xxx.xxx.xxx:/ "
        }
    }

    stage('Build mysql') {
        dir("/var/lib/jenkins/workspace/initial-deploy/kubernetes") {
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@xxx.xxx.xxx.xxx 'kubectl apply -f /mysql.yml'"
        }
    }
    
    stage('Build python') {
        dir("/var/lib/jenkins/workspace/initial-deploy/kubernetes") {
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@xxx.xxx.xxx.xxx 'kubectl apply -f /python.yml'"
        }
    }
}