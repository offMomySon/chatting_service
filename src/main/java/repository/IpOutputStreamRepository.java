package repository;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * ip address 별 outputstream 을 관리하는 역할.
 */
public class IpOutputStreamRepository {
    private final Map<String, BufferedWriter> value = Collections.synchronizedMap(new HashMap<>());

    public void remove(String hostAddress){
        value.remove(hostAddress);
    }

    public void put(String hostAddress, BufferedWriter out){
        value.put(hostAddress, out);
    }

    public BufferedWriter get(String hostAddress){
        return value.get(hostAddress);
    }

    public boolean contain(String hostAddress) {
        return value.containsKey(hostAddress);
    }

    public Collection<BufferedWriter> values(){
        return value.values();
    }

    public int getSize(){
        return value.size();
    }
}
