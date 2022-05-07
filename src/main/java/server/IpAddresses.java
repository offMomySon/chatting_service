package server;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.NonNull;

/**
 * 모든 ip 수신 가능 여부를 알려주는 역할
 */
public class IpAddresses {
    private static final List<String> ALL_USER_SYMBOL = List.of("*", "all");

    private final List<String> ips;
    private final boolean isAllUser;

    private IpAddresses(@NonNull List<String> ips, boolean allUser) {
        this.ips = ips;
        this.isAllUser = allUser;
    }

    public static IpAddresses from(String ...ips){
        boolean isAllUser = Arrays.stream(ips).anyMatch(ALL_USER_SYMBOL::contains);

        if(isAllUser){
            return new IpAddresses(Collections.emptyList(), true);
        }
        return new IpAddresses(List.of(ips), false);
    }

    public boolean isAllUser(){
        return isAllUser;
    }

    public List<String> getIps(){
        if(isAllUser()){
            return Collections.emptyList();
        }
        return ips;
    }
}
