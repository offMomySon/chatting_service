package server.validate;

import server.type.Cmd;

/**
 * general cmd 를 validate 하는 역할.
 */
public class GeneralCmdValidator extends CmdValidator{
    private static final char CMD_STARTER = '/';

    public CmdValidateResult validate(String cmd){
        String[] cmds = cmd.split(" ");

        if(Cmd.notGeneric(cmds[0])){
            return CmdValidateResult.fail("Fail, Invalid general type.");
        }

        if(isValidIps(cmds[1])){
            return CmdValidateResult.fail("Fail, Invalid ip.");
        }

        return CmdValidateResult.success();
    }
}
