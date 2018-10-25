#!/bin/sh
kill `cat run.pid` || true

USER_HOME="$(echo -n $(bash -c "cd ~${USER} && pwd"))"
nohup java -jar -Dspring.profiles.active=prod loyalty-program-0.0.1-SNAPSHOT.jar > $USER_HOME/logs/server.log & echo $! > run.pid
