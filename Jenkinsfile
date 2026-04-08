pipeline {
    agent any

    environment {
        SLACK_WEBHOOK_URL = credentials('slack-webhook-url')
        TEAMS_WEBHOOK_URL = credentials('teams-webhook-url')
        TEAM_LEAD_EMAIL   = 'abdiyow587@gmail.com'
    }

    stages {
        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test -Djava.awt.headless=true'
            }
        }
    }

    post {
        success {
            script {
                sendSlackNotification(':white_check_mark:', 'SUCCESS', 'good')
            }
        }
        failure {
            script {
                sendSlackNotification(':x:', 'FAILURE', 'danger')
                sendTeamsNotification()
                sendEmailNotification()
            }
        }
        unstable {
            script {
                sendSlackNotification(':warning:', 'UNSTABLE', 'warning')
            }
        }
    }
}

def sendSlackNotification(emoji, status, color) {
    def payload = """{"attachments":[{"color":"${color}","text":"${emoji} Build ${status} - Job: ${env.JOB_NAME} #${env.BUILD_NUMBER} - Console: ${env.BUILD_URL}console"}]}"""
    bat """
        curl -s -X POST -H "Content-Type: application/json" -d "${payload.replace('"', '\\"')}" "%SLACK_WEBHOOK_URL%"
    """
}

def sendTeamsNotification() {
    def payload = """{"@type":"MessageCard","@context":"https://schema.org/extensions","themeColor":"FF0000","summary":"Build Failed","sections":[{"activityTitle":"Build FAILED","activitySubtitle":"${env.JOB_NAME} - Build #${env.BUILD_NUMBER}","facts":[{"name":"Job","value":"${env.JOB_NAME}"},{"name":"Build","value":"#${env.BUILD_NUMBER}"},{"name":"Console","value":"${env.BUILD_URL}console"}]}]}"""
    bat """
        curl -s -X POST -H "Content-Type: application/json" -d "${payload.replace('"', '\\"')}" "%TEAMS_WEBHOOK_URL%"
    """
}

def sendEmailNotification() {
    def previousResult = currentBuild.previousBuild?.result
    boolean isTransition = (previousResult == null || previousResult == 'SUCCESS' || previousResult == 'UNSTABLE')

    if (isTransition) {
        emailext(
            subject: "[BUILD FAILED] ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            body: """
                <html>
                <body>
                    <h2 style="color:red;">Build Failed!</h2>
                    <p><b>Job:</b> ${env.JOB_NAME}</p>
                    <p><b>Build Number:</b> #${env.BUILD_NUMBER}</p>
                    <p><b>Console Output:</b> <a href="${env.BUILD_URL}console">Click Here</a></p>
                    <p><b>Previous Status:</b> ${previousResult ?: 'N/A'}</p>
                </body>
                </html>
            """,
            mimeType: 'text/html',
            to: "${env.TEAM_LEAD_EMAIL}"
        )
    } else {
        echo "No email sent — build was already failing."
    }
}