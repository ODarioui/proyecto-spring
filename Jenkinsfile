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

        stage('Sonar') {
            steps {
              withSonarQubeEnv('sonar') {
                sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=rh -Dsonar.projectName='rh' -Dsonar.host.url=http://sonarqube:9000 -Dsonar.token=sqp_43a0ac1a5e3d771895cda7bef13014aa34dc95b0'
              }
            }
        }
    }

}
