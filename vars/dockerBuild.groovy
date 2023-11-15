def call(String dockerHubUsername, String imageName) {
    // Build the Docker image
    sh "docker build --build-arg REACT_APP_RAPID_API_KEY=148b460da7mshff45009e83633abp13cb57jsn436a576e91f9 -t ${imageName} ."
     // Tag the Docker image
    sh "docker tag ${imageName} ${dockerHubUsername}/${imageName}:latest"
    // Push the Docker image
    withDockerRegistry([url: 'https://index.docker.io/v1/', credentialsId: 'docker']) {
        sh "docker push ${dockerHubUsername}/${imageName}:latest"
    }
}