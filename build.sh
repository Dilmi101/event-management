#!/bin/bash
set -e

echo "=== Building proto-common artifact ==="
podman build -t proto-common:latest ./proto-common

echo "=== Building and starting services ==="
podman compose up --build -d
