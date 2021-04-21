# web-server

A simple HTTP web server that binds to specified port via environment variable `SERVER_PORT`.

## Usage

### Application Jar

1. Build the application using `mvn clean install`.
2. Execute `CONTENT_ROOT_PATH=/tmp/static java -jar target/web-server-1.0-SNAPSHOT.jar` replacing `CONTENT_ROOT_PATH` with absolute path on disk with static files.
3. Open up http://localhost:8080 in the browser.

### Docker

The docker-compose file can be used to start up the application. 

Run `docker-compose up -d`. The docker container uses a volume binding to `/tmp/static` on the host to refer and serve static files.

Once the server has started, open up `http://localhost:8080` in the browser.
