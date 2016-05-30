#!/usr/bin/env bash



DEPLOY_USER="travis"
DEPLOY_HOST="chardan.net"
DEPLOY_SSH_PORT=2222
DATABASE_USER="advisorapp"
DEPLOY_PATH="/var/www/advisor_app"
PATH_PROJECT="."
#PROJECT_VERSION=`mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version`

echo "Copy run.sh on the production server"
sshpass -p $DEPLOY_PASS scp -v -P $DEPLOY_SSH_PORT "$PATH_PROJECT/deploy/run.sh" $DEPLOY_USER@$DEPLOY_HOST:$DEPLOY_PATH

echo "Stop application instance"
sshpass -p $DEPLOY_PASS ssh -p $DEPLOY_SSH_PORT $DEPLOY_USER@$DEPLOY_HOST "bash $DEPLOY_PATH/run.sh stop"

echo "Copy .war on the server"
sshpass -p $DEPLOY_PASS scp -v -P $DEPLOY_SSH_PORT "$PATH_PROJECT/target/api-0.1.0.war" $DEPLOY_USER@$DEPLOY_HOST:$DEPLOY_PATH

echo "Run application war"
sshpass -p $DEPLOY_PASS ssh -p $DEPLOY_SSH_PORT $DEPLOY_USER@$DEPLOY_HOST "bash $DEPLOY_PATH/run.sh start $DATABASE_USER $DATABASE_PASS"