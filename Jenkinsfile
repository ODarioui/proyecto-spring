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

        stage("Sonar") {
            steps {
                withSonarQubeEnv('SonarQube Scanner') {
                    sh 'mvn clean package sonar:sonar'
                }
            }
        }

        stage('Docker build') {
          steps {
            sh 'chmod +x ./deliver.sh'
            script {
              docker.withRegistry('https://registry.hub.docker.com', 'docker-hub') {
                def appmavenjenkins = docker.build("dariouio/grh:${gitcommit}", ".")
                appmavenjenkins.push()
              }
            }  
          }  
        }

    }

}
