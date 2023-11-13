@Library('Jenkins_shared_library') 

def COLOR_MAP = [
    'FAILURE' : 'danger',
    'SUCCESS' : 'good'
]

pipeline {
    agent any

    parameters {
        choice(name: 'action', choices: 'create\ndelete', description: 'Select create or destroy.')
    }

    environment {
        SCANNER_HOME = tool 'sonar-scanner'
    }

    stages {
        stage('clean workspace') {
            steps {
                cleanWs()
            }
        }

        stage('checkout from Git') {
            steps {
                checkout([$class: 'GitSCM', 
                          branches: [[name: 'main']], 
                          doGenerateSubmoduleConfigurations: false, 
                          extensions: [], submoduleCfg: [], 
                          userRemoteConfigs: [[url: 'https://github.com/Gaetanneo/Youtube-clone-App.git']]])
            }
        }

        stage('sonarqube Analysis') {
            when {
                expression { params.action == 'create' }
            }
            steps {
                script {
                    echo "Running SonarQube Analysis"
                    // Add steps for SonarQube Analysis
                }
            }
        }

        stage('sonarqube QualitGate') {
            when {
                expression { params.action == 'create' }
            }
            steps {
                script {
                    def credentialsId = 'Sonar-token'
                    echo "Running SonarQube Quality Gate"
                    qualityGate(credentialsId)
                }
            }
        }

        stage('Npm') {
            when {
                expression { params.action == 'create' }
            }
            steps {
                echo "Running npmInstall"
                npmInstall()
            }
        }
    }

    post {
        always {
            echo 'Slack Notifications'
            slackSend (
                channel: '#jenkins_cicd_youtube',
                color: COLOR_MAP[currentBuild.currentResult],
                message: "*${currentBuild.currentResult}:* Job ${env.JOB_NAME} \n build ${env.BUILD_NUMBER} \n More info at: ${env.BUILD_URL}"
            )
        }
    }
}
