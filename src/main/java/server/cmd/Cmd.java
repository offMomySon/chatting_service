package server.cmd;


import common.SimpleMessageFormat;
import java.util.List;
import server.domain.IpAddress;

public interface Cmd {
    List<IpAddress> getIpAddresses();

    SimpleMessageFormat createSMF();
}
