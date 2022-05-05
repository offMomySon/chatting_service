package server.cmd.factory;

import org.apache.commons.lang3.StringUtils;
import server.cmd.Cmd;
import server.cmd.GeneralCmd;
import server.cmd.type.CmdType;
import static server.cmd.type.CmdType.SEND;

public class GeneralCmdFactory implements Factory{
    private static final CmdType cmdType = SEND;
    private static final int partSize = 3;

    @Override
    public Cmd create(String cmd) {
        String[] cmds = cmd.split(" ",partSize);

        String msg = cmds[2];

        return new GeneralCmd(cmdType, msg);
    }

    @Override
    public boolean isCreatable(String cmd) {
        String[] cmds = cmd.split(" ");
        String type = cmds[0];

        if(cmds.length < partSize){
            return false;
        }
        if(cmdType.notEqual(type)){
            return false;
        }

        return true;
    }
}
