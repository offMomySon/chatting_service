package server.sender.validator;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class AddressValidator {
    private static final List<String> ALL_ADDRESS = List.of("*", "all");
    private static final int MAX_ADDRESS_NUM = 255;
    private static final String IP_ADDRESS_DELIMITER = ",";

    public boolean notValid(String address){
        return isValid(address);
    }

    private boolean isValid(String address){
        if(isAllAddress(address)){
            return true;
        }

        if(isIpAddressFormat(address)){
            return true;
        }

        return false;
    }

    private boolean isAllAddress(String address){
        return ALL_ADDRESS.stream().anyMatch(_address -> StringUtils.equalsIgnoreCase(_address, address));
    }

    private boolean isIpAddressFormat(String ipAddress) {
        String[] splitIpAddress = ipAddress.split(IP_ADDRESS_DELIMITER);

        return Arrays.stream(splitIpAddress).allMatch(partAddress-> Integer.parseInt(partAddress) <= MAX_ADDRESS_NUM);
    }
}
