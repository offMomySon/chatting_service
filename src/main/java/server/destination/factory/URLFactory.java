package server.destination.factory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import server.destination.Destination;
import server.destination.factory.DestinationFactory;
import server.destination.url.Url;

public class URLFactory implements DestinationFactory {
    private static final String URL_PREFIX = "http://";
    private static final String ADDRESS_DELIMITER = ",";

    @Override
    public List<Destination> createDestinations(String sDestinations) {
        String[] splitDestinations = sDestinations.split(ADDRESS_DELIMITER);

        List<Destination> destinations;
        try{
            destinations = Arrays.stream(splitDestinations)
                .map(Url::new)
                .collect(Collectors.toList());
        } catch (Exception e){
            return Collections.emptyList();
        }

        return destinations;
    }

    private boolean isContainPrefix(String des){
        return des.indexOf(URL_PREFIX) == 0;
    }
}
