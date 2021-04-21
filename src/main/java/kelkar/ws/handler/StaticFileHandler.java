package kelkar.ws.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class StaticFileHandler {
    private final String CONTENT_ROOT = System.getenv("CONTENT_ROOT_PATH") != null ?
            System.getenv("CONTENT_ROOT_PATH") : "/usr/app/static";

    public String getFileContent(String path) throws IOException {
        String filePath = CONTENT_ROOT + File.separator + path;
        File file = new File(filePath);
        StringBuilder stringBuilder = new StringBuilder();

        System.out.println(filePath);
        System.out.println(file.getAbsolutePath());

        if(!file.exists()) {
            throw new FileNotFoundException();
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()){
                stringBuilder.append(scanner.nextLine());
            }
        } catch (FileNotFoundException fne) {
            throw fne;
        }

        return stringBuilder.toString();
    }
}
