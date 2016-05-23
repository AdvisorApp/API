#!/usr/bin/env bash

PROJECT_VERSION="0.1.0"
#`mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version`

echo "Project version $PROJECT_VERSION"

echo "Copy .war on the server"
sshpass -p $DEPLOY_PASS scp -v -P $DEPLOY_SSH_PORT "/home/travis/build/AdvisorApp/API/target/api-$PROJECT_VERSION.war" $DEPLOY_USER@$DEPLOY_HOST:$DEPLOY_PATH

echo "Run application war"
sshpass -p $DEPLOY_PASS ssh -p $DEPLOY_SSH_PORT $DEPLOY_USER:$DEPLOY_PASS@$DEPLOY_HOST "cd $DEPLOY_PATH && nohup java -Dspring.profiles.active=production -Dspring.datasource.username=$DATABASE_USER -Dspring.datasource.password=$DATABASE_PASS -jar api-$PROJECT_VERSION.war &"