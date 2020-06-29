echo '*****************************'
echo $PCMR_MYSQL_DATABASE
echo $MYSQL_ROOT_PASSWORD
mysql -u root -p$MYSQL_ROOT_PASSWORD -e \
"CREATE DATABASE IF NOT EXISTS $PCMR_MYSQL_DATABASE;
GRANT ALL PRIVILEGES ON $PCMR_MYSQL_DATABASE.* TO $PCMR_MYSQL_USER@'%' IDENTIFIED BY '$PCMR_MYSQL_PASSWORD' WITH GRANT OPTION;"
mysql -u root -p$MYSQL_ROOT_PASSWORD -e \
"CREATE DATABASE IF NOT EXISTS $KEYCLOAK_MYSQL_DATABASE;
GRANT ALL PRIVILEGES ON $KEYCLOAK_MYSQL_DATABASE.* TO $KEYCLOAK_MYSQL_USER@'%' IDENTIFIED BY '$KEYCLOAK_MYSQL_PASSWORD' WITH GRANT OPTION;"
echo '*****************************'