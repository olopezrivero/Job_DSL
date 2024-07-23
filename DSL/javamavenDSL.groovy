job('Java Maven App DSL') {
    description('Java Maven App con DSL para el curso de Jenkins')
    scm {
        git('https://github.com/olopezrivero/Job_DSL.git', 'master') { node ->
            node / gitConfigName('olopezrivero')
            node / gitConfigEmail('olopezrivero@zeni.com.ar')
        }
    }
    steps {
        maven {
            mavenInstallation('mavenjenkins')
            goals('-B -DskipTests clean package')
        }
        maven {
            mavenInstallation('mavenjenkins')
            goals('test')
        }
        shell('''
          echo "Entrega: Desplegando la aplicación"
          java -jar "/var/jenkins_home/workspace/Java Maven App DSL/target/my-app-1.0-SNAPSHOT.jar"
        ''')
    }
    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit('target/surefire-reports/*.xml')
    }
    properties {
        office365ConnectorWebhooks {
            webhooks {
                webhook {
                    name('Jenkins-DSL')
                    url('https://enriquezeni.webhook.office.com/webhookb2/920c20af-d214-4648-b537-105f2efeffec@9d616da3-f444-48df-aea4-ae00aeb3403e/JenkinsCI/0085251523d94bc587b316e9895d17f4/1c549eee-f4e1-479b-89fd-96e25e656e91')
                    startNotification(false)
                    notifySuccess(true)
                    notifyAborted(false)
                    notifyNotBuilt(false)
                    notifyUnstable(true)
                    notifyFailure(true)
                    notifyBackToNormal(true)
                    notifyRepeatedFailure(false)
                    timeout(30000)
                }
            }
        }
    }
}

