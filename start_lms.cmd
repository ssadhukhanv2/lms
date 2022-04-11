rmdir /s "./lms-databases/mongodb/data"
rmdir /s "./lms-databases/mongodb/log"
rmdir /s "./lms-databases/postgresql/data"
rmdir /s "./lms-databases/postgresql/log"

docker-compose --env-file .env -f docker-compose.yml up