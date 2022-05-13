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
    private static final String MESSAGE_DELIMITER = " ";

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

    public static SimpleMessageFormat create(@NonNull String smfMessage){
        int typeDelimiterIdx = smfMessage.indexOf(TYPE_DELIMITER);
        String cmdTypeCode = smfMessage.substring(0, typeDelimiterIdx);
        String leftMessage = smfMessage.substring(typeDelimiterIdx+1);


        CmdType cmdType = CmdType.findByCode(Integer.parseInt(cmdTypeCode));
        if(cmdType.isGeneralType()){
            return new SimpleMessageFormat(cmdType, null, leftMessage);
        }

        if(cmdType.isNoticeType()){
            int messageDelimiterIdx = leftMessage.indexOf(MESSAGE_DELIMITER);
            String decoratedNotice = leftMessage.substring(0, messageDelimiterIdx);
            String message = leftMessage.substring(messageDelimiterIdx+1);

            NoticeType noticeType = NoticeType.findFromDecoratedNotice(decoratedNotice);
            return new SimpleMessageFormat(cmdType, noticeType, message);
        }

        throw new RuntimeException("Not SimpleMessageFormat Stirng.");
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
        sb.append(cmdType.getCode()).append(TYPE_DELIMITER).append(noticeType.decorate()).append(MESSAGE_DELIMITER).append(message);

        return sb.toString();
    }

    public String decodeForConsole(){
        StringBuilder sb = new StringBuilder();

        if(Objects.nonNull(noticeType)){
            return sb.append(noticeType.decorate()).append(MESSAGE_DELIMITER).append(message).toString();
        }
        return sb.append(message).toString();
    }

    public String decodeForFile(){
        StringBuilder sb = new StringBuilder();

        if(Objects.nonNull(noticeType)){
            return sb.append(noticeType.getTag()).append(MESSAGE_DELIMITER).append(message).toString();
        }
        return sb.append(message).toString();
    }
}
