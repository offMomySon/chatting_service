package client.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import lombok.NonNull;
import static util.IoUtil.createWriter;

public class MessageSender {
    private final BufferedWriter out;

    private MessageSender(@NonNull BufferedWriter out) {
        this.out = out;
    }

    public static MessageSender create(@NonNull OutputStream out){
        return new MessageSender(createWriter(out));
    }

    public void send(String message){
        try {
            out.write(message);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
