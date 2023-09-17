docker network create db-net;
docker network connect db-net postgres-db;
docker network connect db-net pgadmin;