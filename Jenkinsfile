pipeline {
    agent {
        label 'docker'
    }

    environment {
        DOCKERHUB_USERNAME = 'vlonje20'
        DOCKERHUB_PASSWORD = 'legion-fibulas-mordacious'
        DOCKERHUB_REPO = 'vlonje20/vin-init-app'
        IMAGE_TAG = 'latest'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout code from version control
                git 'https://github.com/vlonje20/init-app.git'
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

                    # Push the Docker image to Docker Hub
                    docker push $DOCKERHUB_REPO:$IMAGE_TAG
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
                    docker run -d -p 8280:8080 $DOCKERHUB_REPO:$IMAGE_TAG
                    '''
                }
            }
        }
    }
}
