package server.consumer;


import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import repository.IpOutputStreamRepository;
import server.domain.IpAddress;

/**
 * 모든 ip 들에 대해 cmd 를 consume 하는 역할.
 */
public class AllIpSupplier implements IpSupplier{
    private final IpOutputStreamRepository ipRepository;

    public AllIpSupplier(@NonNull IpOutputStreamRepository ipRepository) {
        this.ipRepository = ipRepository;
    }

    public List<IpAddress> supplier(){
        return getAllIps();
    }

    private List<IpAddress> getAllIps(){
        return new ArrayList<>(ipRepository.getKeySet());
    }
}
