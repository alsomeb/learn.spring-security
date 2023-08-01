FROM postgres
ENV POSTGRES_DB moviedb
COPY sql/ /docker-entrypoint-initdb.d/
EXPOSE 5432