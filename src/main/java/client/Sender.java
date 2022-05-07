package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import util.IoUtil;
import static util.IoUtil.createWriter;

@Slf4j
class Sender implements Runnable {
    private final BufferedReader reader = IoUtil.createReader(System.in);
    private final BufferedWriter out;

    private Sender(@NonNull BufferedWriter out) {
        this.out = out;
    }

    public static Sender create(@NonNull OutputStream outputStream){
        return new Sender(createWriter(outputStream));
    }

    public void run() {
        String line;
        try {
            while((line = reader.readLine()) != null){
                log.info("console write : {}", line);

                out.write(line);
                out.flush();
            }
        } catch(IOException e) {
            throw new RuntimeException("Fail message send.", e);
        }
    }
}
