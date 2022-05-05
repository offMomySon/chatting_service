package server.cmd;

import lombok.NonNull;
import server.cmd.type.CmdType;

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
