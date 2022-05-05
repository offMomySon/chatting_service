package server.cmd;


import lombok.NonNull;
import server.cmd.type.CmdType;

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
