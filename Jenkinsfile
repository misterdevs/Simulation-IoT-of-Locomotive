pipeline {
    agent any
    environment {
        SONAR_TOKEN = credentials('sonarqube-token') 
    }
    stages {
        stage('Cleanup') {
            steps {
                sh 'rm -rf Simulation-IoT-of-Locomotive || true'
            }
        }
        stage('Build') {
            steps {
               dir('Simulation-IoT-of-Locomotive/sil_scheduler') {
                    sh './mvnw clean package'
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
               dir('Simulation-IoT-of-Locomotive/sil_scheduler') {
                    withSonarQubeEnv('SonarQube') {
                    sh './mvnw sonar:sonar \
                        -Dsonar.projectKey=sping-boot \
                        -Dsonar.host.url=http://sonarqube:9000 \
                        -Dsonar.login=${SONAR_TOKEN}'
                    }
                }
            }
        }
        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}
