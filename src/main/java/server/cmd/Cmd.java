package server.cmd;

import common.SimpleMessageFormat;
import lombok.NonNull;
import server.cmd.type.CmdType;

/**
 * cmd type, msg 에 따라 적절한 SMF 포멧의 string 을 생성하는 역할.
 */
public interface Cmd {

    SimpleMessageFormat createSMF();
}
