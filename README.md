# web-server

A simple HTTP web server that binds to specified port via environment variable `SERVER_PORT`.

## Usage

To start the server, one can use the docker-compose file to start up the application. 

Run `docker-compose up -d`. The docker container uses a volume binding to `/tmp/static` on the host to refer and serve static files.

Once the server has started, open up `http://localhost:8080` in the browser.
