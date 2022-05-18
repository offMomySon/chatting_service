package server.validate;

import server.type.Cmd;
import server.type.Notice;

/**
 * notice cmd 를 validate 하는 역할.
 */
public class NoticeCmdValidator extends CmdValidator {
    public CmdValidateResult validate(String cmd){
        String[] cmds = cmd.split(" ");

        if(Cmd.notNotice(cmds[0])){
            return CmdValidateResult.fail("Fail, Invalid general type.");
        }

        if(Notice.notExist(cmds[1])){
            return CmdValidateResult.fail("Fail, Invalid notice type.");
        }

        if(isValidIps(cmds[2])){
            return CmdValidateResult.fail("Fail, Invalid ip.");
        }

        return CmdValidateResult.success();
    }
}
