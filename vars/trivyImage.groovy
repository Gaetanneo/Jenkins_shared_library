def call() {
    sh 'trivy image gaetanneo/youtube:latest > trivyimage.txt'
}