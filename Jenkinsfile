pipeline {
    agent any

    environment {
        // Define environment variables
        NEXUS_VERSION = '1.0.0'
        NEXUS_REPOSITORY = 'vin-init-app-snapshot'
        NEXUS_URL = 'http://localhost:8081/repository/vin-init-app-snapshot//${NEXUS_REPOSITORY}'
        SONAR_PROJECT_KEY = 'squ_b5599386184a4ed95255da444bbd4466d04f64d6'
        TOMCAT_URL = 'http://http://localhost:8082/vin-init-app'
        TOMCAT_CREDENTIALS_ID = 'jenkins_tom_cred'
    }

    stages {
        stage('Checkout') {
            steps {
                // Get the latest code from the source control
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                // Run Maven build
                sh 'mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Run SonarQube analysis
                script {
                    def scannerHome = tool 'SonarQube Scanner';
                    withSonarQubeEnv('SonarQube') {
                        sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=${SONAR_PROJECT_KEY}"
                    }
                }
            }
        }

        stage('Archive to Nexus') {
            steps {
                // Upload artifact to Nexus
                script {
                    def artifact = "target/my-app-${env.BUILD_NUMBER}.jar"
                    sh "curl -u admin:admin123 --upload-file ${artifact} ${NEXUS_URL}/${artifact}"
                }
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                // Deploy to Tomcat
                script {
                    def tomcatManagerUrl = "${TOMCAT_URL}/manager/text"
                    def warFile = "target/my-app-${env.BUILD_NUMBER}.war"
                    sh """
                        curl -u \$(credentials('${TOMCAT_CREDENTIALS_ID}')) \
                        --upload-file ${warFile} "${tomcatManagerUrl}/deploy?path=/myapp&update=true"
                    """
                }
            }
        }
    }

    post {
        success {
            echo 'Build, scan, archive, and deployment completed successfully.'
        }
        failure {
            echo 'The pipeline failed.'
        }
    }
}