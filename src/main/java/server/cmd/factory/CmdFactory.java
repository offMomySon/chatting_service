package server.cmd.factory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import server.cmd.Cmd;
import server.cmd.GeneralCmd;
import server.cmd.NoticeCmd;
import server.cmd.type.CmdType;
import server.cmd.type.NoticeType;
import server.domain.IpAddress;

/**
 * cmd 를 생성하는 역할.
 */
public class CmdFactory {

    public Cmd create(String cmd) {
        String[] cmds = cmd.split(" ");

        if(CmdType.isGeneralType(cmds[0])){
            return createGeneralType(cmds);
        }

        return createNoticeType(cmds);
    }

    private Cmd createGeneralType(String[] cmds) {
        CmdType cmdType = CmdType.findGeneralType(cmds[0]);
        List<IpAddress> ipAddresses = getIpAddresses(cmds[1]);

        StringBuilder msg = new StringBuilder();
        for (int i = 2; i < cmds.length; i++) {
            msg.append(cmds[i]).append(" ");
        }

        return new GeneralCmd(cmdType, ipAddresses, msg.toString());
    }

    private Cmd createNoticeType(String[] cmds) {
        CmdType cmdType = CmdType.findNoticeType(cmds[0]);
        NoticeType noticeType = NoticeType.find(cmds[1]);
        List<IpAddress> ipAddresses = getIpAddresses(cmds[2]);

        StringBuilder msg = new StringBuilder();
        for (int i = 3; i < cmds.length; i++) {
            msg.append(cmds[i]).append(" ");
        }

        return new NoticeCmd(cmdType, noticeType, ipAddresses, msg.toString());
    }

    private List<IpAddress> getIpAddresses(String ips) {
        return Arrays.stream(ips.split(","))
            .map(IpAddress::create)
            .collect(Collectors.toList());
    }
}
