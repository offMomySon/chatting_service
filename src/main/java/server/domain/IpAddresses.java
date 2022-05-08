package server.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.NonNull;

/**
 * 모든 ip 수신 가능 여부를 알려주는 역할
 */
public class IpAddresses {
    private final List<IpAddress> ips;
    private final boolean isAllUser;

    public IpAddresses(@NonNull List<IpAddress> ips, boolean allUser) {
        this.ips = ips;
        this.isAllUser = allUser;
    }

    public boolean isAllUser(){
        return isAllUser;
    }

    public List<IpAddress> getIps(){
        if(isAllUser()){
            return Collections.emptyList();
        }
        return ips;
    }
}
