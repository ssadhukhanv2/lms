#!/bin/sh

#/extras/wait-for.sh lms-ui-db-postgres:$LMSUI_POSTGRES_HOST_PORT "Postgres Started"
/extras/wait-for.sh -t 120 lms-ui-db-postgres:5432 -- echo "Postgres Started"

java -cp @/app/jib-classpath-file @/app/jib-main-class-file

