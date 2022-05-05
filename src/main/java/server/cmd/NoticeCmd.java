package server.cmd;

import lombok.NonNull;
import server.cmd.type.CmdType;
import server.cmd.type.NoticeType;

public class NoticeCmd extends Cmd{
    private final NoticeType noticeType;
    private static final String msgDelimiter = " ";

    public NoticeCmd(CmdType type, @NonNull NoticeType noticeType, @NonNull String msg) {
        super(type, msg);
        this.noticeType = noticeType;
    }

    @Override
    public String createSMF() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.getValue()).append(smfDelimiter);
        sb.append(noticeType.getPrevEncoding()).append(noticeType.getTag()).append(noticeType.getPostEncoding());
        sb.append(msgDelimiter);
        sb.append(msg);

        return sb.toString();
    }
}
