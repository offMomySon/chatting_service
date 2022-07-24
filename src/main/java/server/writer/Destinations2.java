package server.writer;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.NonNull;

/**
 * 역할.
 * 어떤 행동을 수행하는 Destination 들.
 */
public class Destinations2 {
    private final Predicate<Destination> destinationPredicate;

    private Destinations2(@NonNull Predicate<Destination> destinationPredicate) {
        this.destinationPredicate = destinationPredicate;
    }

    public void forEach(Collection<Destination> destinations, Consumer<Destination> destinationConsumer){
        Collection<Destination> findDestinations = destinations.stream().filter(destinationPredicate).collect(Collectors.toUnmodifiableList());

        findDestinations.forEach(destinationConsumer);
    }

    public static class Builder{
        private Predicate<Destination> destinationPredicate = (destination -> true);

        public Builder filtered(@NonNull Predicate<Destination> destinationPredicate){
            this.destinationPredicate = this.destinationPredicate.and(destinationPredicate);
            return this;
        }

        public Destinations2 build(){
            return new Destinations2(destinationPredicate);
        }
    }

}
