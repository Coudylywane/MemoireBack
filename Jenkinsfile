pipeline {
    agent any
    tools {
        // Utilisez le JDK fourni par Jenkins
        jdk 'jdk'
        // Utilisez Maven fourni par Jenkins
        maven 'maven'
    }
    stages {
        stage('Clean') {
            steps {
                // Nettoyer le projet
                sh 'mvn -B clean'
            }
        }
        stage('Test') {
            steps {
                // Exécuter les tests en utilisant Maven
                sh 'mvn test'
            }
            post {
                always {
                    // Rapport des résultats de test JUnit
                    junit allowEmptyResults: true, testResults: '*/test-results/*.xml'
                }
            }
        }
        stage('Build') {
            steps {
                // Construire le projet en utilisant Maven et ignorer les tests
                sh 'mvn -B -DskipTests clean package'
            }
        }
    }
}
