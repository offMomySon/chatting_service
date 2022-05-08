package server.cmd;


import common.SimpleMessageFormat;
import server.cmd.type.CmdType;

/**
 * cmd type, msg 에 따라 적절한 SMF 포멧의 string 을 생성하는 역할.
 */
public class GeneralCmd implements Cmd{
    private final CmdType type;
    private final String msg;

    public GeneralCmd(CmdType type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    @Override
    public SimpleMessageFormat createSMF() {
        return SimpleMessageFormat.create(type, msg);
    }
}
