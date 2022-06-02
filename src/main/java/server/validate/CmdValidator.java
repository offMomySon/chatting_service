package server.validate;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * cmd 를 validate 하고
 * validate 중 ip 를 validate 하는 역할.
 */
public abstract class CmdValidator {
    private static final Pattern IPV4_PATTERN = Pattern.compile("^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\\.(?!$)|$)){4}$");
    private static final Pattern IP_ALL_MATCH_PATTERN = Pattern.compile("^(all)|(\\*)");
    private static final String IP_DELIMITER = ",";

    protected boolean isValidIps(String sIps){
        String[] ips = sIps.split(IP_DELIMITER);

        return Arrays.stream(ips)
            .anyMatch(CmdValidator::notValidIp);
    }

    private static boolean notValidIp(String ip){
        Matcher ipv4Matcher = IPV4_PATTERN.matcher(ip);
        Matcher ipAllMatcher = IP_ALL_MATCH_PATTERN.matcher(ip);

        return !ipv4Matcher.matches() && !ipAllMatcher.matches();
    }
}
