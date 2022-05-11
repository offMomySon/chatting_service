package server.consumer.factory;

import java.util.List;
import repository.IpOutputStreamRepository;
import server.consumer.AllIpSupplier;
import server.consumer.IpSupplier;
import server.consumer.RequestedIpSupplier;
import server.domain.IpAddress;

public class IpSupplierFactory {

    private final IpOutputStreamRepository ipOutputStreamRepository;

    public IpSupplierFactory(IpOutputStreamRepository ipOutputStreamRepository) {
        this.ipOutputStreamRepository = ipOutputStreamRepository;
    }

    public IpSupplier create(List<IpAddress> ipAddresses){
        if(isExistAllMatchedIp(ipAddresses)){
            return new AllIpSupplier(ipOutputStreamRepository);
        }
        return new RequestedIpSupplier(ipOutputStreamRepository, ipAddresses);
    }

    private boolean isExistAllMatchedIp(List<IpAddress> ipAddresses){
        return ipAddresses.stream().anyMatch(IpAddress::isAllMatchIp);
    }

}
