package server.cmd;

import common.SimpleMessageFormat;
import java.util.List;
import lombok.NonNull;
import server.cmd.type.CmdType;
import server.cmd.type.NoticeType;
import server.domain.IpAddress;

/**
 * cmd type, msg, noticeType 에 따라 적절한 SMF 포멧의 string 을 생성하는 역할.
 */
public class NoticeCmd implements Cmd {
    private final CmdType type;
    private final NoticeType noticeType;
    private final List<IpAddress> ipAddresses;
    private final String msg;

    public NoticeCmd(@NonNull CmdType type, NoticeType noticeType, List<IpAddress> ipAddresses, String msg) {
        this.type = type;
        this.noticeType = noticeType;
        this.ipAddresses = ipAddresses;
        this.msg = msg;
    }

    @Override
    public List<IpAddress> getIpAddresses() {
        return ipAddresses;
    }

    @Override
    public SimpleMessageFormat createSMF() {
        return new SimpleMessageFormat(type, noticeType, msg);
    }
}
