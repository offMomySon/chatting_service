package server.v4;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import lombok.NonNull;
import static util.IoUtil.createWriter;

public class SimpleFormatMessageWriter implements WriterV4{
    private final BufferedWriter out;

    private SimpleFormatMessageWriter(@NonNull BufferedWriter out) {
        this.out = out;
    }

    public static SimpleFormatMessageWriter create(@NonNull OutputStream outputStream){
        BufferedWriter writer = createWriter(outputStream);

        System.out.println("create smf writer");
        return new SimpleFormatMessageWriter(writer);
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
