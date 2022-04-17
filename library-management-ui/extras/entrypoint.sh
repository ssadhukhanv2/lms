#!/bin/sh

# we can't use wait-for-it.sh as it's a bash script
# and jdk-alpine doesn't have bash installed
# https://docs.docker.com/compose/startup-order/
# https://github.com/eficode/wait-for
#/extras/wait-for.sh lms-ui-db-postgres:$LMSUI_POSTGRES_HOST_PORT "Postgres Started"
/extras/wait-for.sh -t 120 lms-ui-db-postgres:5432 -- echo "Postgres Started. Starting Library Management UI";

java -cp @/app/jib-classpath-file @/app/jib-main-class-file;

