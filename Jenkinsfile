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

        stage('Build') {
            steps {
                sh 'mvn -version'
                sh 'mvn -B -DskipTests clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }

}
