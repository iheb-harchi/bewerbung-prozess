pipeline {
    agent any

    environment {
        NEXUS_URL = 'http://localhost:8081'
        NEXUS_REPO = 'bewerbung-app'
        CREDENTIALS_ID = 'nexus'

    stages {
        // Stage 1: Version aus Maven auslesen
        stage('Read Version') {
            steps {
                script {
                    env.ARTIFACT_ID = sh(
                        script: 'mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout',
                        returnStdout: true
                    ).trim()
                    
                    env.VERSION = sh(
                        script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout',
                        returnStdout: true
                    ).trim()
                    
                    echo "Building ${ARTIFACT_ID}-${VERSION}.war"
                }
            }
        }

        // Stage 2: Build mit Maven
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests' // Schneller Build ohne Tests
            }
        }

        // Stage 3: Upload nach Nexus
        stage('Deploy to Nexus') {
            steps {
                script {
                    // WAR-Datei mit Version im Namen (z. B. bewerbung-app-1.0.0.war)
                    def warFile = "target/${ARTIFACT_ID}-${VERSION}.war"
                    
                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: NEXUS_URL,
                        groupId: 'org.mycompany', // Anpassen!
                        version: VERSION,
                        repository: NEXUS_REPO,
                        credentialsId: 'nexus-credentials',
                        artifacts: [
                            [
                                artifactId: ARTIFACT_ID,
                                classifier: '',
                                file: warFile,
                                type: 'war'
                            ]
                        ]
                    )
                    
                    echo "✅ ${warFile} erfolgreich nach Nexus hochgeladen"
                }
            }
        }
    }

    // Nachbereitung
    post {
        success {
            archiveArtifacts "target/*.war" // WAR als Download in Jenkins sichern
        }
        failure {
            slackSend channel: '#build-failures',
                     message: "❌ Build fehlgeschlagen: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        }
    }
}
