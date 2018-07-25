node {
        def mvnHome
        def pom
	def commit_id
	def ecrRepo = "862349788439.dkr.ecr.eu-west-1.amazonaws.com"
        
    	stage('checkout'){
        
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
		withCredentials([usernamePassword(credentialsId: 'AWS-ECR', passwordVariable: 'ecrPassword', usernameVariable: 'ecrUser')]){
		    sh ("docker build -t ${pom.artifactId}:${commit_id} .")
		    sh "docker login -u ${env.ecrUser} -p ${env.ecrPassword} https://${ecrRepo}"
		    sh "docker tag ${pom.artifactId}:${commit_id} ${ecrRepo}/${pom.artifactId}:${commit_id}"
		    sh ("docker push ${ecrRepo}/${pom.artifactId}:${commit_id}")
		}
    	}
    	
    	stage('deploy to k8s'){
		    sh "kubectl set image deployment/k8s-demo -n staging k8s-demo=${ecrRepo}/${pom.artifactId}:${commit_id}"
    	}
}

