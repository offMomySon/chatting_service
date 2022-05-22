package server.destination.factory;

import java.util.List;
import server.destination.Destination;

public interface DestinationFactory {
    public abstract List<Destination> createDestinations(String sDestinations);
}
