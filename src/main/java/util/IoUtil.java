package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import lombok.NonNull;
import static java.nio.charset.StandardCharsets.UTF_8;

public class IoUtil {
    public static BufferedReader createReader(@NonNull InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream, 8192), UTF_8),8192);
    }

    public static BufferedWriter createWriter(@NonNull OutputStream outputStream) {
        return new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(outputStream,8192), UTF_8),8192);
    }

    public static BufferedWriter createFileAppender(@NonNull File file) {
        try {
            return new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file, true),8192), UTF_8),8192);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found.", e);
        }
    }

    public static BufferedWriter createFileAppender(@NonNull String fileName) {
        try {
            return new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(fileName, true),8192), UTF_8),8192);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found.", e);
        }
    }
}
