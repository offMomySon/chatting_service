package server.cmd.factory;

import server.cmd.Cmd;
import server.cmd.GeneralCmd;
import server.cmd.type.CmdType;


public class GeneralCmdCmdFactory implements CmdFactory {
    private static final int partSize = 3;

    @Override
    public Cmd create(String cmd) {
        String[] cmds = cmd.split(" ",partSize);

        String msg = cmds[2];

        return new GeneralCmd(CmdType.find(cmds[0]), msg);
    }

    @Override
    public boolean isCreatable(String cmd) {
        String[] cmds = cmd.split(" ");
        String type = cmds[0];

        if(cmds.length < partSize){
            return false;
        }
        if(CmdType.notExistGeneralType(cmd)){
            return false;
        }

        return true;
    }
}
