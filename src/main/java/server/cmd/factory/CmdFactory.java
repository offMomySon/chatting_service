package server.cmd.factory;

import server.cmd.Cmd;

public interface CmdFactory {
    Cmd create(String cmd);

    boolean isCreatable(String cmd);
}
