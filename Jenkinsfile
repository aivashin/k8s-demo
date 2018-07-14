node {
        def mvnHome
        def pom
        
    	stage('checkout'){
        
	        git url: 'https://github.com/aivashin/k8s-demo.git'
        	mvnHome = tool 'M3'
		pom = readMavenPom file: 'pom.xml'

        	sh "git rev-parse HEAD > commit-id"
	        def commit_id = readFile('commit-id').trim()
        	println commit_id
	}

        stage('build'){
	        sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean install"
        }
        
        stage('create & upload docker image'){
		sh ("docker build -t halfback/${pom.artifactId}:latest .")
		withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]){
		sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
		sh ("docker push halfback/${pom.artifactId}:latest")
		}
    	}
    	
    	stage('deploy to k8s'){
    	    sh 'kubectl delete -f deployment.yaml || true' 
    	    sh 'kubectl create -f deployment.yaml --validate=false'
    	}
}

