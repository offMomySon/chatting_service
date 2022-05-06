package server.cmd;

import lombok.NonNull;
import server.cmd.type.CmdType;

/**
 * cmd type, msg 에 따라 적절한 SMF 포멧의 string 을 생성하는 역할.
 */
public abstract class Cmd {
    protected static final String smfDelimiter = ":";

    protected final CmdType type;
    protected final String msg;

    protected Cmd(@NonNull CmdType type, @NonNull String msg) {
        this.type = type;
        this.msg = msg;
    }

    public abstract String createSMF();
}
