#! /bin/bash

APPLICATION_PROPERTIES='./backend/src/main/resources/db/application.properties'

touch $APPLICATION_PROPERTIES

echo $DB_URL >> $APPLICATION_PROPERTIES
echo $DB_USERNAME >> $APPLICATION_PROPERTIES
echo $DB_PASSWORD >> $APPLICATION_PROPERTIES
echo 'spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.hikari.data-source-properties.useSSL=true
spring.datasource.hikari.data-source-properties.requireSSL=true

spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=30000
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.max-lifetime=1800000' >> $APPLICATION_PROPERTIES
