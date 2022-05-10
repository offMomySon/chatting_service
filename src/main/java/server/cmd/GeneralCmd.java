package server.cmd;


import common.SimpleMessageFormat;
import java.util.List;
import server.cmd.type.CmdType;
import server.domain.IpAddress;

/**
 * cmd type, msg 에 따라 적절한 SMF 포멧의 string 을 생성하는 역할.
 */
public class GeneralCmd implements Cmd {
    private final CmdType type;
    private final List<IpAddress> ipAddresses;
    private final String msg;

    public GeneralCmd(CmdType type, List<IpAddress> ipAddresses, String msg) {
        this.type = type;
        this.ipAddresses = ipAddresses;
        this.msg = msg;
    }

    public List<IpAddress> getIpAddresses() {
        return ipAddresses;
    }

    @Override
    public SimpleMessageFormat createSMF() {
        return SimpleMessageFormat.create(type, msg);
    }
}
