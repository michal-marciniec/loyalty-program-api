#!/bin/sh
kill `cat run.pid` || true
pkill java || true

USER_HOME="$(echo -n $(bash -c "cd ~${USER} && pwd"))"
FRONTEND_HOME=file:///home/loyalty/frontend
SAMPLE_DB_DATA_PATH=classpath:flyway/sample

nohup java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9999 -jar -Dspring.profiles.active=prod -Dloyalty-program.db.samplesPath="$SAMPLE_DB_DATA_PATH" -Dspring.resources.static-locations="$FRONTEND_HOME" loyalty-program-api-0.0.1-SNAPSHOT.jar > $USER_HOME/logs/server.log & echo $! > run.pid
