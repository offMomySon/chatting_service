package server.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpAddress {
    /**
     * 25[0-5] = 250-255
     * (2[0-4])[0-9]  = 200-249
     * (1[0-9])[0-9]  = 100-199
     * ([1-9])[0-9]   = 10-99
     * [0-9]          = 0-9
     * (\.(?!$))      = can't end with a dot
     */
    private static final String IPV4_PATTERN= "^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\\.(?!$)|$)){4}$";
    private static final Pattern pattern = Pattern.compile(IPV4_PATTERN);

    private final String value;

    public IpAddress(String value) {
        this.value = validateIpAddress(value);
    }

    private static String validateIpAddress(String ipAddress){
        if(Objects.isNull(ipAddress)){
            throw new RuntimeException("IpAddress is null.");
        }

        Matcher matcher = pattern.matcher(ipAddress);
        if(!matcher.matches()){
            throw new RuntimeException("Not ip address pattern.");
        }

        return ipAddress;
    }

    public String getValue() {
        return value;
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
