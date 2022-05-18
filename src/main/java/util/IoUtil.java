package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import lombok.NonNull;
import static java.nio.charset.StandardCharsets.UTF_8;

public class IoUtil {
    public static BufferedReader createReader(@NonNull InputStream inputStream){
        return new BufferedReader(new InputStreamReader(new BufferedInputStream(inputStream), UTF_8));
    }

    public static BufferedWriter createWriter(@NonNull OutputStream outputStream){
        return new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(outputStream), UTF_8));
    }

    public static ObjectInputStream createObjectInputStream(@NonNull InputStream inputStream){
        try {
            return new ObjectInputStream(new BufferedInputStream(inputStream));
        } catch (IOException e) {
            throw new RuntimeException("Fail to create ObjectInput stream.", e);
        }
    }

    public static ObjectOutputStream createObjectOutputStream(@NonNull OutputStream outputStream){
        try {
            return new ObjectOutputStream(new BufferedOutputStream(outputStream));
        } catch (IOException e) {
            throw new RuntimeException("Fail to create ObjectOutput stream.", e);
        }
    }


    public static BufferedWriter createFileAppender(@NonNull File file){
        try {
            return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true), UTF_8));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found.",e);
        }
    }
}
