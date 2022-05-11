package server.actor;

import server.cmd.Cmd;
import server.domain.IpAddress;

public interface Actor {
    void accept(IpAddress ipAddress, Cmd cmd);
}
