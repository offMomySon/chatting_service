package client;

import client.writer.console.ConsoleWriteStrategy;
import client.writer.console.ConsoleWriteStrategyFactory;
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
    private final ConsoleWriteStrategyFactory consoleWriteStrategyFactory;

    private Receiver(@NonNull BufferedReader in, @NonNull ConsoleWriteStrategyFactory consoleWriteStrategyFactory) {
        this.in = in;
        this.consoleWriteStrategyFactory = consoleWriteStrategyFactory;
    }

    public static Receiver create(@NonNull InputStream inputStream,
                                  @NonNull ConsoleWriteStrategyFactory consoleWriteStrategyFactory) {
        return new Receiver(createReader(inputStream), consoleWriteStrategyFactory);
    }

    public void waitAndThenGetMsg() {
        String smfMessage = END_CONNECTION;
        try {
            while (!Objects.equals(smfMessage = in.readLine(), END_CONNECTION)) {
                log.info("From server : {}", smfMessage);

                ConsoleWriteStrategy consoleWriteStrategy = consoleWriteStrategyFactory.create(smfMessage);
                consoleWriteStrategy.write();


            }
        } catch (IOException e) {
            throw new RuntimeException("Fail to receive msg.", e);
        }
    }
}
