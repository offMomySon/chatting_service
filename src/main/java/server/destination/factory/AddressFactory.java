package server.destination.factory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import server.destination.Destination;
import server.destination.address.AllAddress;
import server.destination.address.IpAddress;

/**
 * address 를 생성하기 위한 factory 객체.
 */
public class AddressFactory implements DestinationFactory {
    private static final Set<String>  ALL_MATCHED = Set.of("*", "all");
    private static final String ADDRESS_DELIMITER = ",";

    @Override
    public List<Destination> createDestinations(String sDestinations) {
        String[] splitDestinations = sDestinations.split(ADDRESS_DELIMITER);

        List<Destination> destinations;
        try {
            destinations = Arrays.stream(splitDestinations)
                .map(AddressFactory::create)
                .collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }

        return destinations;
    }

    private static Destination create(String destination){
        if(ALL_MATCHED.contains(destination)){
            return new AllAddress();
        }
        return new IpAddress(destination);
    }
}
