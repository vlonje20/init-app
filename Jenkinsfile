pipeline {
    agent {
        label 'vin-agent1'
    }

    environment {
        DOCKERHUB_USERNAME = 'vlonje20'
        DOCKERHUB_PASSWORD = 'legion-fibulas-mordacious'
        DOCKERHUB_REPO = 'vlonje20/vin-image'
        IMAGE_TAG = 'latest'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout code from version control
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[url: 'https://github.com/vlonje20/init-app.git', credentialsId: 'jenkins-github-cred']]
                ])
            }
        }

        stage('Build WAR') {
            steps {
                script {
                    sh '''
                    # Build the .war file using Maven
                    mvn clean package
                    '''
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh '''
                    # Log in to Docker Hub
                    echo "$DOCKERHUB_PASSWORD" | docker login -u "$DOCKERHUB_USERNAME" --password-stdin

                    # Build the Docker image
                    docker build -t $DOCKERHUB_REPO:$IMAGE_TAG .
                    '''
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    sh '''
                    # Pull the Docker image (optional, just to verify the push)
                    docker pull $DOCKERHUB_REPO:$IMAGE_TAG

                    # Run the Docker container
                    docker run -d -p 8080:8080 $DOCKERHUB_REPO:$IMAGE_TAG
                    '''
                }
            }
        }
    }
}
