pipeline {
    agent any

    environment {
        NEXUS_URL = 'http://localhost:8081'
        NEXUS_REPO = 'bewerbung-app'
        CREDENTIALS_ID = 'nexus'
    }

    stages {
        stage('Read Maven Coordinates') {
            steps {
                script {
                    env.GROUP_ID = sh(script: "mvn help:evaluate -Dexpression=project.groupId -q -DforceStdout", returnStdout: true).trim()
                    env.ARTIFACT_ID = sh(script: "mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout", returnStdout: true).trim()
                    env.VERSION = sh(script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
                }
                echo "Using coordinates: $GROUP_ID:$ARTIFACT_ID:$VERSION"
            }
        }
        
        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Upload to Nexus') {
            steps {
                nexusArtifactUploader artifacts: [[
                    artifactId: env.ARTIFACT_ID,
                    classifier: '',
                    file: "target/${env.ARTIFACT_ID}-${env.VERSION}.war",
                    type: 'war'
                ]],
                credentialsId: env.CREDENTIALS_ID,
                groupId: env.GROUP_ID,
                nexusUrl: env.NEXUS_URL,
                nexusVersion: 'nexus3',
                protocol: 'http',
                repository: env.NEXUS_REPO,
                version: env.VERSION
            }
        }
    }
}
