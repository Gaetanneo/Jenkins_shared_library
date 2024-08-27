def call() {
    sh 'trivy image gaetanneo/youtube2:latest > trivyimage.txt'
}