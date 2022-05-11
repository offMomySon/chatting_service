package server.consumer;

import java.util.List;
import server.domain.IpAddress;

public interface IpSupplier {
    List<IpAddress> supplier();
}
