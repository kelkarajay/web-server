docker-build: \
    docker build -t web-server .

docker-run: \
    docker run --rm -it -p 8080:8080 web-server:latest