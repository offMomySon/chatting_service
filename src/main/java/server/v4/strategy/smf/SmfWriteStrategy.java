package server.v4.strategy.smf;

import server.v4.message.smf.SimpleMessageFormatV4;

public interface SmfWriteStrategy {
    void write(SimpleMessageFormatV4 simpleMessageFormatV4);
}
