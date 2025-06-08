package functions;

import java.io.FileReader;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        try(FileReader fileReader = new FileReader("file1.txt")) {
            System.out.println(fileReader.read());
        }
    }
}
