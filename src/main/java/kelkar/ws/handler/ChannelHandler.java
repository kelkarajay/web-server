package kelkar.ws.handler;

import kelkar.ws.model.HttpRequest;
import kelkar.ws.model.HttpResponse;
import kelkar.ws.model.HttpStatus;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

public class ChannelHandler {
    private final StaticFileHandler staticFileHandler;

    public ChannelHandler(StaticFileHandler staticFileHandler) {
        this.staticFileHandler = staticFileHandler;
    }

    public void handleChannelAccept(Selector selector, SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();

        SocketChannel socketChannel = serverSocketChannel.accept(); // can be non-blocking
        if (socketChannel != null) {
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        }
    }

    public void handleChannelRead(Selector selector, SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        socketChannel.read(buffer); // can be non-blocking
        buffer.flip();

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.parseRequest(new String(buffer.array()).trim());

        socketChannel.register(selector, SelectionKey.OP_WRITE, httpRequest);
    }

    public void handleChannelWrite(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        HttpRequest httpRequest = (HttpRequest) selectionKey.attachment();

        String testResponse = "Hello World! " + httpRequest.toString();

        HashMap<String, String> responseHeadersMap = new HashMap<>();
        responseHeadersMap.put("Content-Type", "text/html");
        responseHeadersMap.put("Date", java.time.LocalDateTime.now().toString());

        HttpResponse httpResponse;

        String path = "";
        try {
            if (httpRequest.getUri() != null && httpRequest.getUri().equals("/")) {
                path = "index.html";
            } else if (httpRequest.getUri() != null) {
                path = httpRequest.getUri();
            } else {
                path = "index.html";
            }

            String content = staticFileHandler.getFileContent(path);
            httpResponse = new HttpResponse(HttpStatus.OK, responseHeadersMap, content);
            System.out.println(String.format("Serving file %s in response to request %s", path, httpRequest.getUri()));
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println(String.format("File not found %s in response to request %s", path, httpRequest.getUri()));
            httpResponse = new HttpResponse(HttpStatus.NOT_FOUND, responseHeadersMap, "NOT FOUND");
        } catch (Exception e) {
            httpResponse = new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, responseHeadersMap, "INTERNAL SERVER ERROR");
        }

        socketChannel.write(ByteBuffer.wrap(httpResponse.build().getBytes())); // can be non-blocking
        socketChannel.close();
    }
}
