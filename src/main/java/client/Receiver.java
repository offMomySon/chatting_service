package client;

import common.protocol.SimpleMessageFormat;
import common.view.console.ConsoleMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import static util.IoUtil.createObjectInputStream;
import static util.IoUtil.createReader;

@Slf4j
class Receiver {
    private static final SimpleMessageFormat END_CONNECTION = null;
    public static final char[] BUFFER = new char[8192];

    private final ObjectInputStream in;

    private Receiver(@NonNull ObjectInputStream in) {
        this.in = in;
    }

    public static Receiver create(@NonNull InputStream inputStream){
        return new Receiver(createObjectInputStream(inputStream));
    }

    public void waitAndThenGetMsg() {
        SimpleMessageFormat simpleMessageFormat = END_CONNECTION;
        try{
            while( (simpleMessageFormat = (SimpleMessageFormat) in.readObject()) != END_CONNECTION ){
                ConsoleMessage consoleMessage = simpleMessageFormat.createConsoleForm();

                log.info("From server : {}", consoleMessage.create());

//                SimpleMessageFormat simpleMessageFormat = SimpleMessageFormat.create(msg);
//
//                log.info(simpleMessageFormat.decodeForConsole());
//
//                FileRecorder fileRecorder = new FileRecorder("서버");
//                fileRecorder.accept(simpleMessageFormat.decodeForFile());
            }
        } catch(IOException e) {
            throw new RuntimeException("Fail to receive msg.",e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
