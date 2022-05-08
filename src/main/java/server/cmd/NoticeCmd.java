package server.cmd;

import common.SimpleMessageFormat;
import lombok.NonNull;
import server.cmd.type.CmdType;
import server.cmd.type.NoticeType;

/**
 * cmd type, msg, noticeType 에 따라 적절한 SMF 포멧의 string 을 생성하는 역할.
 */
public class NoticeCmd implements Cmd{
    private final CmdType type;
    private final NoticeType noticeType;
    private final String msg;

    public NoticeCmd(@NonNull CmdType type, NoticeType noticeType, String msg) {
        this.type = type;
        this.noticeType = noticeType;
        this.msg = msg;
    }

    @Override
    public SimpleMessageFormat createSMF() {
        return new SimpleMessageFormat(type, noticeType, msg);
    }
}
