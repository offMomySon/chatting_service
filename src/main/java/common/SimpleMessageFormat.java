package common;

import java.text.MessageFormat;
import java.util.Objects;
import lombok.NonNull;
import server.cmd.type.CmdType;
import server.cmd.type.NoticeType;

public class SimpleMessageFormat {
    private static final String TYPE_DELIMITER = ":";

    private final CmdType cmdType;
    private final NoticeType noticeType;
    private final String message;

    public SimpleMessageFormat(@NonNull CmdType cmdType, NoticeType noticeType, @NonNull String message) {
        this.cmdType = cmdType;
        this.noticeType = noticeType;
        this.message = message;
    }

    public String createMsg(){
        if(Objects.isNull(noticeType)){
            return createNotExistNoticeTypeMsg();
        }
        return createExistNoticeTypeMsg();
    }

    private String createNotExistNoticeTypeMsg(){
        StringBuilder sb = new StringBuilder();
        sb.append(cmdType.getValue()).append(TYPE_DELIMITER).append(message);

        return sb.toString();
    }

    private String createExistNoticeTypeMsg(){
        StringBuilder sb = new StringBuilder();
        sb.append(cmdType.getValue()).append(TYPE_DELIMITER).append(noticeType.decorate(message));

        return sb.toString();
    }
}
