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
public class Destinations {
    private final List<Destination> value;

    public Destinations(@NonNull Collection<Destination> value) {
        this.value = validate(value);
    }

    private static List<Destination> validate(Collection<Destination> value){
        if(Objects.isNull(value)){
            throw new RuntimeException("value is null.");
        }
        if(value.isEmpty()){
            throw new RuntimeException("value is empty.");
        }

        List<Destination> newDestinations = value.stream().filter(v -> !Objects.isNull(v)).collect(Collectors.toUnmodifiableList());

        if(newDestinations.isEmpty()){
            throw new RuntimeException("newDestinations is empty.");
        }

        return newDestinations;
    }

    public Destinations filtered(Predicate<Destination> destinationPredicate){
        List<Destination> newValue = value.stream().filter(destinationPredicate).collect(Collectors.toUnmodifiableList());
        return new Destinations(newValue);
    }

    public void forEach(Consumer<Destination> destinationConsumer){
        value.forEach(destinationConsumer);
    }

}
