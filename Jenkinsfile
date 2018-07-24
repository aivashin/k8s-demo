node {
        def mvnHome
        def pom
	def commit_id
        
    	stage('checkout'){
        
//	        git url: 'https://github.com/aivashin/k8s-demo.git'
		checkout scm
        	mvnHome = tool 'M3'
		pom = readMavenPom file: 'pom.xml'

        	sh "git rev-parse HEAD > commit-id"
	        commit_id = readFile('commit-id').trim()
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
		sh "kubectl patch deployment k8s-demo -n staging -p '{\"spec\":{\"template\":{\"spec\":{\"containers\":[{\"name\":\"k8s-demo\",\"env\":[{\"name\":\"commit_id\",\"value\":\"${commit_id}\"}]}]}}}}'"
    	}
}

