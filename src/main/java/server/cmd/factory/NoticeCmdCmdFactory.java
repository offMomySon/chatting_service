package server.cmd.factory;

import server.cmd.Cmd;
import server.cmd.NoticeCmd;
import server.cmd.type.CmdType;
import server.cmd.type.NoticeType;
import static server.cmd.type.CmdType.NOTICE;

public class NoticeCmdCmdFactory implements CmdFactory {
    private static final CmdType cmdType = NOTICE;
    private static final int partSize = 4;

    @Override
    public Cmd create(String cmd) {
        String[] cmds = cmd.split(" ",partSize);

        String noticeCmd = cmds[1];
        String msg = cmds[2];

        return new NoticeCmd(cmdType, NoticeType.find(noticeCmd), msg);
    }

    @Override
    public boolean isCreatable(String cmd) {
        String[] cmds = cmd.split(" ");
        String type = cmds[0];
        String noticeType = cmds[1];

        if(cmds.length < partSize){
            return false;
        }
        if(cmdType.notEqual(type)){
            return false;
        }
        if(NoticeType.notContain(noticeType)){
            return false;
        }

        return true;
    }
}
