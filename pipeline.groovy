node {
    stage('Clone Project') {
          git branch: "main",
              url: 'https://github.com/fatih-dagli/kubernetes-env-creation-with-aws.git'
    }

    stage('Build Project') {
        dir("/var/lib/jenkins/workspace/initial-deploy/kubernetes") {
            sh "aws ecr get-login-password --region <region> | docker login --username AWS --password-stdin aaaaaaaaaaaaa.dkr.ecr.<region>.amazonaws.com"
            sh "docker build -t python:built ."
            sh "docker tag python:built aaaaaaaaaaaaa.dkr.ecr.<region>.amazonaws.com/python:latest"
            sh "docker push aaaaaaaaaaaaa.dkr.ecr.<region>.amazonaws.com/python:latest"
            sh "sed -i 's/bbbbbbbbbbbbbb/aaaaaaaaaaaaa/g' /var/lib/jenkins/workspace/initial-deploy/kubernetes/python.yml"
            sh "sed -i 's/_region_/<region>/g' /var/lib/jenkins/workspace/initial-deploy/kubernetes/python.yml"
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
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@xxx.xxx.xxx.xxx 'aws configure set aws_access_key_id xxxxxxxxxxxxxxxxxxxxxxxxxxxxx'"
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@yyy.yyy.yyy.yyy 'aws configure set aws_access_key_id xxxxxxxxxxxxxxxxxxxxxxxxxxxxx'"
        }
    }

    stage('set aws_secret_access_key') {
        dir("/var/lib/jenkins/workspace/initial-deploy/kubernetes") {
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@xxx.xxx.xxx.xxx 'aws configure set aws_secret_access_key yyyyyyyyyyyyyyyyyyyyyyyyyyyyy'"
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@yyy.yyy.yyy.yyy 'aws configure set aws_secret_access_key yyyyyyyyyyyyyyyyyyyyyyyyyyyyy'"
        }
    }

    stage('login to aws registry') {
        dir("/var/lib/jenkins/workspace/initial-deploy/kubernetes") {
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@xxx.xxx.xxx.xxx 'aws ecr get-login-password --region <region> | docker login --username AWS --password-stdin aaaaaaaaaaaaa.dkr.ecr.<region>.amazonaws.com'"
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@yyy.yyy.yyy.yyy 'aws ecr get-login-password --region <region> | docker login --username AWS --password-stdin aaaaaaaaaaaaa.dkr.ecr.<region>.amazonaws.com'"
        }
    }
    
    stage('Build python') {
        dir("/var/lib/jenkins/workspace/initial-deploy/kubernetes") {
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@xxx.xxx.xxx.xxx 'kubectl delete secret regcred --ignore-not-found'"
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@xxx.xxx.xxx.xxx 'kubectl create secret docker-registry regcred --docker-server=aaaaaaaaaaaaa.dkr.ecr.<region>.amazonaws.com --docker-username=AWS --docker-password=\$(aws ecr get-login-password --region <region>)'"
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@xxx.xxx.xxx.xxx 'kubectl apply -f /python.yml'"
            sh "sshpass -p 'Operation123' ssh -o StrictHostKeyChecking=no root@xxx.xxx.xxx.xxx 'kubectl rollout restart deployments python'"
        }
    }
}