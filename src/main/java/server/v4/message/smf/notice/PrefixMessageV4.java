package server.v4.message.smf.notice;

public class PrefixMessageV4 {
    private final String message;

    public PrefixMessageV4(String message) {
        this.message = message;
    }

    public String create() {
        return message;
    }
}
