#!/bin/bash
set -e

echo "=== Building proto-common artifact ==="
podman build -t proto-common:latest ./proto-common

echo "=== Extracting proto-common artifacts ==="
rm -rf ./proto-common/build/repo
CID=$(podman create proto-common:latest)
podman cp "$CID":/repo ./proto-common/build/repo
podman rm "$CID"

echo "=== Copying proto-common artifacts into service build contexts ==="
for SERVICE in event-service registration-service; do
  rm -rf "./$SERVICE/proto-repo"
  cp -r ./proto-common/build/repo "./$SERVICE/proto-repo"
done

echo "=== Building and starting services ==="
podman compose up --build -d
