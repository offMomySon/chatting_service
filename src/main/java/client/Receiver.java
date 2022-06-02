package client;

import client.format.SimpleMessageFormat;
import common.view.console.ConsoleMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Objects;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import static util.IoUtil.createObjectInputStream;
import static util.IoUtil.createReader;

@Slf4j
class Receiver {
    private static final String END_CONNECTION = null;
    public static final char[] BUFFER = new char[8192];

    private final BufferedReader in;

    private Receiver(@NonNull BufferedReader in) {
        this.in = in;
    }

    public static Receiver create(@NonNull InputStream inputStream){
        return new Receiver(createReader(inputStream));
    }

    public void waitAndThenGetMsg() {
        String smfMessage = END_CONNECTION;
        try{
            while(!Objects.equals(smfMessage = in.readLine(), END_CONNECTION)) {

                log.info("From server : {}", smfMessage);





            }
        } catch(IOException e) {
            throw new RuntimeException("Fail to receive msg.",e);
        }
    }
}
