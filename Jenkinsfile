pipeline {
    agent any

    environment {
        gitcommit = "${gitcommit}"
        SONAR_LOGIN = credentials('sonar')
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

        stage('Sonar') {
            steps {
              withSonarQubeEnv('sonar') {
                sh """
                    mvn sonar:sonar \ 
                    -Dsonar.host.url=sonarqube:9000
                    -Dsonar.login
                """
              }
            }
        }
    }

}
