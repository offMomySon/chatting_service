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
    private final Map<InetAddress, BufferedWriter> value = Collections.synchronizedMap(new HashMap<>());

    public void remove(InetAddress inetAddress){
        value.remove(inetAddress);
    }

    public void put(InetAddress inetAddress, BufferedWriter out){
        value.put(inetAddress, out);
    }

    public BufferedWriter get(InetAddress inetAddress){
        return value.get(inetAddress);
    }

    public Collection<BufferedWriter> values(){
        return value.values();
    }

    public int getSize(){
        return value.size();
    }
}
