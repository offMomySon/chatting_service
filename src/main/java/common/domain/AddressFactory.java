package common.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddressFactory {
    private static final List<String> ALL_MATCHED = List.of("*", "all");
    private static final String DELIMITER = ",";

    public List<Address> create(String ipAddresses){
        return Arrays.stream(ipAddresses.split(DELIMITER))
            .map(this::doCreate)
            .collect(Collectors.toList());
    }

    private Address doCreate(String ipAddress){
        if(ALL_MATCHED.contains(ipAddress)){
            return new AllAddress(ipAddress);
        }
        return new SpecificAddress(ipAddress);
    }
}
