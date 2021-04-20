package kelkar.ws.handler;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class ChannelHandlerTest {
    Mockito mockito;

    @BeforeAll
    public void setup(){
        mockito = new Mockito();
    }

    @Test
    public void testHandleChannelAcceptNoPendingConnections() throws IOException {
        SelectionKey selectionKey = mockito.mock(SelectionKey.class);
        Selector selector = mockito.mock(Selector.class);
        ServerSocketChannel serverSocketChannel = mockito.mock(ServerSocketChannel.class);
        SocketChannel socketChannel = mockito.mock(SocketChannel.class);

        mockito.when(selectionKey.channel()).thenReturn(serverSocketChannel);
        mockito.when(serverSocketChannel.accept()).thenReturn(null);

        ChannelHandler.handleChannelAccept(selector, selectionKey);
    }

    @Test
    @Disabled
    public void testHandleChannelAccept() throws IOException {
        SelectionKey selectionKey = mockito.mock(SelectionKey.class);
        Selector selector = mockito.mock(Selector.class);
        ServerSocketChannel serverSocketChannel = mockito.mock(ServerSocketChannel.class);
        SocketChannel socketChannel = mockito.mock(SocketChannel.class);

        mockito.when(selectionKey.channel()).thenReturn(serverSocketChannel);
        mockito.when(serverSocketChannel.accept()).thenReturn(socketChannel);

        ChannelHandler.handleChannelAccept(selector, selectionKey);

        verify(socketChannel, times(1)).configureBlocking(false);
    }
}
