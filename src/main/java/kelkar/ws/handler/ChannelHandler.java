package kelkar.ws.handler;

import kelkar.ws.model.HttpRequest;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ChannelHandler {
    public static void handleChannelAccept(Selector selector, SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();

        SocketChannel socketChannel = serverSocketChannel.accept(); // can be non-blocking
        if (socketChannel != null) {
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        }
    }

    public static void handleChannelRead(Selector selector, SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        socketChannel.read(buffer); // can be non-blocking
        buffer.flip();

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.parseRequest(new String(buffer.array()).trim());

        socketChannel.register(selector, SelectionKey.OP_WRITE, httpRequest);
    }

    public static void handleChannelWrite(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        HttpRequest httpRequest = (HttpRequest) selectionKey.attachment();

        String testResponse = "Hello World! " + httpRequest.toString();

        socketChannel.write(ByteBuffer.wrap(testResponse.getBytes())); // can be non-blocking
        socketChannel.close();
    }
}
