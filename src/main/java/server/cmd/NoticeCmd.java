package server.cmd;

import lombok.NonNull;
import server.cmd.type.CmdType;
import server.cmd.type.NoticeType;

/**
 * cmd type, msg, noticeType 에 따라 적절한 SMF 포멧의 string 을 생성하는 역할.
 */
public class NoticeCmd extends Cmd{
    private final NoticeType noticeType;
    private static final String MSG_DELIMITER = " ";

    public NoticeCmd(CmdType type, @NonNull NoticeType noticeType, @NonNull String msg) {
        super(type, msg);
        this.noticeType = noticeType;
    }

    @Override
    public String createSMF() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.getValue()).append(SMF_DELIMITER);
        sb.append(noticeType.getPrevEncoding()).append(noticeType.getTag()).append(noticeType.getPostEncoding());
        sb.append(MSG_DELIMITER);
        sb.append(msg);

        return sb.toString();
    }
}
