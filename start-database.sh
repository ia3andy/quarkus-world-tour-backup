#!/usr/bin/env bash
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 \
    --name postgres-quarkus-rock -e POSTGRES_USER=quarkus \
    -e POSTGRES_PASSWORD=quarkus -e POSTGRES_DB=rock \
    -p 5432:5432 postgres:13.1
