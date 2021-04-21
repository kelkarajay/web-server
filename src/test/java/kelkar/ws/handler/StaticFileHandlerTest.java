package kelkar.ws.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class StaticFileHandlerTest {

    Mockito mockito;

    @Test
    public void shouldReadFileContents() throws IOException {
        File file = new File("src/test/resources/index.html");

        StaticFileHandler staticFileHandler = new StaticFileHandler("src/test/resources");
        String content = staticFileHandler.getFileContent("index.html");

        Assertions.assertNotNull(content);
    }
}
