package server.actor;

import lombok.NonNull;
import server.cmd.Cmd;
import server.domain.IpAddress;

public interface Actor {
    void accept(@NonNull Cmd cmd);
}
