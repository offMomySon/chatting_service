package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import static java.nio.charset.StandardCharsets.UTF_8;

public class IoUtil {
    public static BufferedReader createReader(InputStream inputStream){
        return new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream), UTF_8));
    }

    public static BufferedWriter createWriter(OutputStream outputStream){
        return new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(outputStream), UTF_8));
    }
}
