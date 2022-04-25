node {
    def mvnHome
    stage('Clone Project') {
          git branch: "master",
              url: 'https://github.com/fatih-dagli/kubernetes-env-creation-with-aws.git'
    }
}