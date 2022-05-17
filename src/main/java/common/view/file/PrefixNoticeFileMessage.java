package common.view.file;

import server.type.Notice;

public class PrefixNoticeFileMessage  implements FileMessage {
    private final String message;

    private PrefixNoticeFileMessage(String message) {
        this.message = message;
    }

    public static PrefixNoticeFileMessage create(Notice type){
        String message = type.getSymbol();

        return new PrefixNoticeFileMessage(message);
    }

    @Override
    public String create() {
        return message;
    }
}
