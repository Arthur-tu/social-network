docker run --name psql -e POSTGRES_DB=users -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -v /tmp/postgres-data:/var/lib/postgresql/data -p 5001:5432 postgres:15