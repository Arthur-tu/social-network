docker run --name postgres-db -e POSTGRES_DB=users-db -e POSTGRES_USER=artur -e POSTGRES_PASSWORD=123 -v /tmp/postgres-data:/var/lib/postgresql/data -p 5001:5432 postgres:15