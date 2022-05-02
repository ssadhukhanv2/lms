::C:\Users\subhr\Softwares\keycloak-18.0.0\bin\kc.bat show-config
::C:\Users\subhr\Softwares\keycloak-18.0.0\bin\kc.bat build --db=postgres
::C:\Users\subhr\Softwares\keycloak-18.0.0\bin\kc.bat start --http-port=3333 --db-url-host=localhost --db-username=authdbadmin --db-url-database=auth_db --db-password=authdbpassword 
docker run --name keycloak_test -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev