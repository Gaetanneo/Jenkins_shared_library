@Library('Jenkins_shared_library') 
def COLOR_MAP = [
    'FAILURE' : 'danger',
    'SUCCESS' : 'good'
]
tools{
        jdk 'jdk17'
        nodejs 'node16'
    }
    environment {
        SCANNER_HOME=tool 'sonar-scanner'
    }

pipeline{
    agent any
    parameters {
        choice(name: 'action', choices: 'create\ndelete', description: 'Select create or destroy.')
    }
    stages{
        stage('clean workspace'){
            steps{
                cleanWorkspace()
            }
        }
        stage('checkout from Git'){
            steps{
                checkoutGit('https://github.com/Gaetanneo/Youtube-clone-App.git', 'main')
            }
        }
        stage('sonarqube Analysis'){
        when { expression { params.action == 'create'}}    
            steps{
                sonarqubeAnalysis()
            }
        }
        stage('sonarqube QualitGate'){
        when { expression { params.action == 'create'}}    
            steps{
                script{
                    def credentialsId = 'Sonar-token'
                    qualityGate(credentialsId)
                }
            }
        }
        stage('Npm'){
        when { expression { params.action == 'create'}}    
            steps{
                npmInstall()
            }
        }
        stage('Trivy file scan'){
        when { expression { params.action == 'create'}}    
            steps{
                trivyFs()
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