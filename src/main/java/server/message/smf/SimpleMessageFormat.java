package server.message.smf;

import server.v5.Message;

public interface SimpleMessageFormat extends Message {
    @Override
    String create();
}
