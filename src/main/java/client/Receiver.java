package client;

import client.writer.CompositedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import static util.IoUtil.createReader;

@Slf4j
class Receiver {
    private static final int END_CONNECTION = 0;

    private final BufferedReader in;
    private final char[] buffer = new char[8192];

    private String smfMessage;
    private int readCount = END_CONNECTION;

    private Receiver(@NonNull BufferedReader in) {
        this.in = in;
    }

    public static Receiver create(@NonNull InputStream inputStream) {
        return new Receiver(createReader(inputStream));
    }

    public void waitAndThenGetMsg() {
        try {
            while ((readCount = in.read(buffer)) != END_CONNECTION) {
                smfMessage = String.valueOf(buffer,0, readCount);

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
