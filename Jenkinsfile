pipeline {
    agent any

    environment {
        gitcommit = "${gitcommit}"
    }

    tools {
        maven 'mavenjenkins'
    }

    stages {

        stage('Verificación SCM') {
            steps {
                script {
                checkout scm
                sh "git rev-parse --short HEAD > .git/commit-id"  
                gitcommit = readFile('.git/commit-id').trim()
                }
          }  
        }
    }

}
