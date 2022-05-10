package server.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IpAddress {
    /**
     * 25[0-5] = 250-255
     * (2[0-4])[0-9]  = 200-249
     * (1[0-9])[0-9]  = 100-199
     * ([1-9])[0-9]   = 10-99
     * [0-9]          = 0-9
     * (\.(?!$))      = can't end with a dot
     */
    private static final Pattern IP_ALL_MATCH_PATTERN = Pattern.compile("^(all)|(\\*)");
    private static final Pattern IPV4_PATTERN = Pattern.compile("^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\\.(?!$)|$)){4}$");

    private final String value;
    private final boolean isAllMatchIp;

    private IpAddress(String value, boolean isAllMatchIp) {
        this.value = validateIpAddress(value);
        this.isAllMatchIp = isAllMatchIp;
    }

    public static IpAddress create(String ipAddress){
        Matcher allMatcher = IP_ALL_MATCH_PATTERN.matcher(ipAddress);
        if(allMatcher.matches()){
            return new IpAddress(ipAddress, true);
        }

        return new IpAddress(ipAddress, false);
    }

    private static String validateIpAddress(String ipAddress){
        if(Objects.isNull(ipAddress)){
            throw new RuntimeException("IpAddress is null.");
        }

        Matcher allMatcher = IP_ALL_MATCH_PATTERN.matcher(ipAddress);
        if(allMatcher.matches()){
            return ipAddress;
        }

        Matcher matcher = IPV4_PATTERN.matcher(ipAddress);
        if(matcher.matches()){
            return ipAddress;
        }

        throw new RuntimeException("Not ip address pattern.");
    }

    public boolean isAllMatchIp() {
        return isAllMatchIp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpAddress ipAddress = (IpAddress) o;
        return Objects.equals(value, ipAddress.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
