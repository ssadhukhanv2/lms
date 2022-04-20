#!/bin/bash
set -e
export PGPASSWORD=$POSTGRES_PASSWORD

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_PASSWORD" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER $APP_DB_USER WITH PASSWORD '$APP_DB_PASS';
    CREATE DATABASE $APP_DB_NAME;
    GRANT ALL PRIVILEGES ON DATABASE $APP_DB_NAME TO $APP_DB_USER;
    \connect $APP_DB_NAME $APP_DB_USER
    BEGIN;
    
    DROP TABLE IF EXISTS lms_ui_role;
    DROP TABLE IF EXISTS lms_ui_user;
    DROP TABLE IF EXISTS user_role_mapping;
    
    create table lms_ui_role
    (
        role_id   bigint       not null
            primary key,
        role_code varchar(255) not null
    );

    alter table lms_ui_role
        owner to $APP_DB_USER;

    -- auto-generated definition
    create table lms_ui_user
    (
        id                          bigint       not null
            primary key,
        account_enabled             boolean,
        account_expired             boolean,
        account_locked              boolean,
        account_credentials_expired boolean,
        email                       varchar(255) not null
            constraint uk_biqeuc5lh05pik80qpembpcbn
                unique,
        user_name                   varchar(255) not null
            constraint uk_5503k5h3xhx5m6vkb95shvvps
                unique,
        password                    varchar(255) not null
    );

    alter table lms_ui_user
        owner to $APP_DB_USER;

    -- auto-generated definition
    create table user_role_mapping
    (
        fk_user_id bigint not null
            constraint fkpfbqbrp04p9bxyv31l7m8j70y
                references lms_ui_user,
        fk_role_id bigint not null
            constraint fkanc41yirobvmnh77dj2m7lpg4
                references lms_ui_role,
        primary key (fk_user_id, fk_role_id)
    );

    alter table user_role_mapping
        owner to $APP_DB_USER;

    INSERT INTO lms_ui_role (role_id, role_code) VALUES (5, 'BUSINESS_USER');
    INSERT INTO lms_ui_role (role_id, role_code) VALUES (6, 'ADMIN_USER');
    INSERT INTO lms_ui_user (id, account_enabled, account_expired, account_locked, account_credentials_expired, email, user_name, password) VALUES (7, true, false, false, false, 'super@gmail.com', 'superuser', '\$2a\$11\$sHhIa4abGDgjEQ3QXRleM.WvnarL88Z0vWUWm/BqVFjq1AYv.hlSy');
    INSERT INTO lms_ui_user (id, account_enabled, account_expired, account_locked, account_credentials_expired, email, user_name, password) VALUES (8, true, false, false, false, 'admin@gmail.com', 'admin', '\$2a\$11\$cT076htJQ7pBgL5J.aUls.VXPTGiRn.r/HpIFEyf7J.P2Vu18g5f6');
    INSERT INTO user_role_mapping (fk_user_id, fk_role_id) VALUES (7, 6);
    INSERT INTO user_role_mapping (fk_user_id, fk_role_id) VALUES (7, 5);
    INSERT INTO user_role_mapping (fk_user_id, fk_role_id) VALUES (8, 6);

    commit;

EOSQL
