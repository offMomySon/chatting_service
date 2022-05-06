package server.cmd;


import lombok.NonNull;
import server.cmd.type.CmdType;

/**
 * cmd type, msg 에 따라 적절한 SMF 포멧의 string 을 생성하는 역할.
 */
public class GeneralCmd extends Cmd{

    public GeneralCmd(CmdType type, @NonNull String msg) {
        super(type, msg);
    }

    @Override
    public String createSMF() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.getValue()).append(smfDelimiter).append(msg);

        return sb.toString();
    }
}
