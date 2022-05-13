package client;

import common.SimpleMessageFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import client.actor.FileRecorder;
import static util.IoUtil.createReader;

@Slf4j
class Receiver {
    private static final int END_CONNECTION = -1;
    public static final char[] BUFFER = new char[8192];

    private final BufferedReader in;

    private Receiver(@NonNull BufferedReader in) {
        this.in = in;
    }

    public static Receiver create(@NonNull InputStream inputStream){
        return new Receiver(createReader(inputStream));
    }

    public void waitAndThenGetMsg() {
        int readCount = END_CONNECTION;
        try{
            while( (readCount = in.read(BUFFER)) != END_CONNECTION ){
                String msg = String.valueOf(BUFFER, 0, readCount);

                log.info("From server : {}", msg);

                SimpleMessageFormat simpleMessageFormat = SimpleMessageFormat.create(msg);

                log.info(simpleMessageFormat.decodeForConsole());

                FileRecorder fileRecorder = new FileRecorder("서버");
                fileRecorder.accept(simpleMessageFormat.decodeForFile());
            }
        } catch(IOException e) {
            throw new RuntimeException("Fail to receive msg.",e);
        }
    }
}
