package kelkar.ws;

import kelkar.ws.handler.ChannelHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {
    private static Selector selector = null;

    public static void main(String args[]) {
        String SERVER_PORT_ENV_KEY = "SERVER_PORT";
        int portNumber = 8080;

        try{
            portNumber = System.getenv(SERVER_PORT_ENV_KEY) != null ? Integer.parseInt(System.getenv(SERVER_PORT_ENV_KEY)): portNumber;
        } catch (NumberFormatException nfe) {
            System.out.println(String.format("Cannot start the server, invalid port number specified - %d.", System.getenv(SERVER_PORT_ENV_KEY)));
            System.exit(1);
        }

        try {
            selector = Selector.open();

            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            ServerSocket serverSocket = socketChannel.socket();

            serverSocket.bind(new InetSocketAddress(portNumber));
            socketChannel.configureBlocking(false);
            System.out.println(String.format("Server started and listening on port %d", portNumber));

            int operations = socketChannel.validOps();
            socketChannel.register(selector, operations);

            while(true) {
                selector.select();

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectedKeys.iterator();

                while(selectionKeyIterator.hasNext()) {
                    SelectionKey key = selectionKeyIterator.next();

                    if(key.isAcceptable()) {
                        System.out.println("Connection acceptable.");
                        ChannelHandler.handleChannelAccept(selector, key);
                    } else if (key.isReadable()) {
                        System.out.println("Connection Readable.");
                        ChannelHandler.handleChannelRead(selector, key);
                    } else if (key.isWritable()) {
                        System.out.println("Connection Writable.");
                        ChannelHandler.handleChannelWrite(key);
                    }

                    selectionKeyIterator.remove();
                }
            }
        } catch (IOException e) {
            System.out.println(String.format("Server encountered a runtime error %s. Exiting", e.getMessage()));
            e.printStackTrace();
            System.exit(1);
        }
    }
}
