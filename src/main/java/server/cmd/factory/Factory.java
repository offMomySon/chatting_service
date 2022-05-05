package server.cmd.factory;

import server.cmd.Cmd;

public interface Factory {
    Cmd create(String cmd);

    boolean isCreatable(String cmd);
}
