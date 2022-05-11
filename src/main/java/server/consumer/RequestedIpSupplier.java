package server.consumer;

import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import repository.IpOutputStreamRepository;
import server.domain.IpAddress;

/**
 * specific Ip 에 대해 cmd 를 consume 하는 역할.
 */
public class RequestedIpSupplier implements IpSupplier{
    private final IpOutputStreamRepository ipRepository;
    private final List<IpAddress> requestIdAddresses;

    public RequestedIpSupplier(@NonNull IpOutputStreamRepository ipRepository, @NonNull List<IpAddress> requestIdAddresses) {
        this.ipRepository = ipRepository;
        this.requestIdAddresses = requestIdAddresses;
    }

    public List<IpAddress> supplier(){
        return getRequestedIpIfExist();
    }

    private List<IpAddress> getRequestedIpIfExist(){
        return requestIdAddresses.stream()
            .filter(ipRepository::contain)
            .collect(Collectors.toList());
    }
}
