def call() {
    withSonarQubeEnv('sonar-server') {
        sh ''' $SCANNER_HOME/bin/sonar-scanner -Dsonar.projectName=test3-slack-notif -Dsonar.projectKey=test3-slack-notif '''
    }
}
