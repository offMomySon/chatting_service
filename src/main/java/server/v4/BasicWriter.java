package server.v4;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import lombok.NonNull;
import static java.nio.charset.StandardCharsets.UTF_8;

public class BasicWriter implements WriterV4{
    private final BufferedWriter out;

    private BasicWriter(@NonNull BufferedWriter out) {
        this.out = out;
    }

    public static BasicWriter create(@NonNull OutputStream outputStream){
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, 8192);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream, UTF_8);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter, 8192);

        return new BasicWriter(bufferedWriter);
    }

    @Override
    public void write(@NonNull String message) {
        try {
            out.write(message);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("write fail.");
        }
    }
}
