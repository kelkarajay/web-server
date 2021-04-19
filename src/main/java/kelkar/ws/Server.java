package kelkar.ws;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String args[]) {
        String SERVER_PORT_ENV_KEY = "SERVER_PORT";
        int portNumber = 8080;

        try{
            portNumber = Integer.parseInt(System.getenv(SERVER_PORT_ENV_KEY));
        } catch (NumberFormatException nfe) {
            System.out.println(String.format("Cannot start the server, invalid port number specified - %d.", System.getenv(SERVER_PORT_ENV_KEY)));
            System.exit(1);
        }

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println(String.format("Server started and listening on port %d", portNumber));

        } catch (IOException e) {
            System.out.println(String.format("Server encountered a runtime error %s. Exiting", e.getMessage()));
            e.printStackTrace();
            System.exit(1);
        }
    }
}
