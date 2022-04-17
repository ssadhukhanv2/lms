#!/bin/sh

# we can't use wait-for-it.sh as it's a bash script
# and jdk-alpine doesn't have bash installed
# https://docs.docker.com/compose/startup-order/
# https://github.com/eficode/wait-for

/extras/wait-for.sh -t 120 lms-db-mongo:27017 -- echo "Mongo Started. Starting Library Management System";

java -cp @/app/jib-classpath-file @/app/jib-main-class-file