package server.validate;

import server.cmd.type.CmdType;

/**
 * general cmd 를 validate 하는 역할.
 */
public class GeneralCmdValidator extends CmdValidator{
    private static final char CMD_STARTER = '/';

    public CmdValidateResult validate(String cmd){
        if(cmd.charAt(0) != CMD_STARTER){
            return CmdValidateResult.fail("Fail, Invalid cmd initiator.");
        }
        cmd = cmd.substring(1);

        String[] cmds = cmd.split(" ");

        if(CmdType.notExistGeneralType(cmds[0])){
            return CmdValidateResult.fail("Fail, Invalid general type.");
        }

        if(inValidIps(cmds[1])){
            return CmdValidateResult.fail("Fail, Invalid ip.");
        }

        return CmdValidateResult.success();
    }
}
