package server.message.smf.notice;

public class PrefixMessage {
    private final String message;

    public PrefixMessage(String message) {
        this.message = message;
    }

    public String create() {
        return message;
    }
}