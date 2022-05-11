package repository;

import java.io.BufferedWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import server.domain.IpAddress;

/**
 * ip address 별 outputstream 을 관리하는 역할.
 */
public class IpOutputStreamRepository {
    private final Map<IpAddress, BufferedWriter> value = Collections.synchronizedMap(new HashMap<>());

    public void remove(IpAddress hostAddress){
        value.remove(hostAddress);
    }

    public void put(IpAddress hostAddress, BufferedWriter out){
        value.put(hostAddress, out);
    }

    public BufferedWriter get(IpAddress hostAddress){
        return value.get(hostAddress);
    }

    public boolean contain(IpAddress hostAddress) {
        return value.containsKey(hostAddress);
    }

    public Set<IpAddress> getKeySet(){return value.keySet();}

    public Collection<BufferedWriter> values(){
        return value.values();
    }

    public int getSize(){
        return value.size();
    }
}
