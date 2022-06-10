package client;

import client.writer.CompositedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import static util.IoUtil.createReader;

@Slf4j
class Receiver {
    private static final String END_CONNECTION = null;

    private final BufferedReader in;

    private Receiver(@NonNull BufferedReader in) {
        this.in = in;
    }

    public static Receiver create(@NonNull InputStream inputStream) {
        return new Receiver(createReader(inputStream));
    }

    public void waitAndThenGetMsg() {
        String smfMessage = END_CONNECTION;
        try {
            while (!Objects.equals(smfMessage = in.readLine(), END_CONNECTION)) {
                log.info("From server : {}", smfMessage);

                SmfDecoder smfDecoder = SmfDecoder.decode(smfMessage);

                CompositedWriter writer = smfDecoder.getWriter();
                writer.write();
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail to receive msg.", e);
        }
    }
}
