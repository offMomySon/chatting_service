package server.destination.factory;

import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import server.destination.Destination;

public class CompositedDestinationFactory implements DestinationFactory{
    private final List<DestinationFactory> destinationFactories;

    private CompositedDestinationFactory(@NonNull List<DestinationFactory> destinationFactories) {
        this.destinationFactories = destinationFactories;
    }

    public static CompositedDestinationFactory from (DestinationFactory ... destinationFactories){
        return new CompositedDestinationFactory(List.of(destinationFactories));
    }

    @Override
    public List<Destination> createDestinations(String sDestinations) {
        return destinationFactories.stream()
            .map(destinationFactory -> destinationFactory.createDestinations(sDestinations))
            .filter(destinations -> !destinations.isEmpty())
            .findAny()
            .orElseThrow(()-> new RuntimeException("생성가능한 Destination 이 존재하지 않습니다."));
    }
}
