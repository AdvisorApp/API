#!/usr/bin/env bash

SERVICE_NAME=AdvisorAppService
PATH_TO_JAR=/var/www/advisor_app/api-0.1.0.war
PID_PATH_NAME=/tmp/AdvisorAppPid
DB_USERNAME=$2
DB_PASSWORD=$3

case $1 in
    start)
        echo "Starting $SERVICE_NAME ..."
        if [ ! -f $PID_PATH_NAME ]; then
            java -jar -Dspring.profiles.active=production -Dspring.datasource.username=$DB_USERNAME -Dspring.datasource.password=$DB_PASSWORD -jar $PATH_TO_JAR >> AdvisorApp.log &
            echo $! > $PID_PATH_NAME
            echo "$SERVICE_NAME started ..."
            echo "$SERVICE_NAME STARTED " >> AdvisorApp.log
        else
            echo "$SERVICE_NAME is already running ..." >> AdvisorApp.log
        fi
    ;;
    stop)
        if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME stoping ..."
            echo "$SERVICE_NAME stopping " >> AdvisorApp.log
            kill $PID;
            echo "$SERVICE_NAME stopped ..."
            echo "$SERVICE_NAME stopped" >> AdvisorApp.log
            rm $PID_PATH_NAME
        else
            echo "$SERVICE_NAME is not running ..." >> AdvisorApp.log
        fi
    ;;
esac