package common;

import java.util.Objects;
import lombok.NonNull;
import server.cmd.type.CmdType;
import server.cmd.type.NoticeType;

/**
 * cmd type, msg 에 따라 적절한 SMF 포멧의 string 을 생성하는 역할.
 */
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

    public static SimpleMessageFormat create(@NonNull CmdType cmdType, @NonNull String message){
        return new SimpleMessageFormat(cmdType, null, message);
    }

    public String createMsg(){
        if(Objects.isNull(noticeType)){
            return createNotExistNoticeTypeMsg();
        }
        return createExistNoticeTypeMsg();
    }

    private String createNotExistNoticeTypeMsg(){
        StringBuilder sb = new StringBuilder();
        sb.append(cmdType.getCode()).append(TYPE_DELIMITER).append(message);

        return sb.toString();
    }

    private String createExistNoticeTypeMsg(){
        StringBuilder sb = new StringBuilder();
        sb.append(cmdType.getCode()).append(TYPE_DELIMITER).append(noticeType.decorate(message));

        return sb.toString();
    }
}
