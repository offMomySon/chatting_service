package server.v5;

import java.util.Objects;
import lombok.Getter;
import lombok.NonNull;
import server.Address;

@Getter
public class Destination {
    private final Address address;
    private final Usage usage;

    public Destination(@NonNull Address address, @NonNull Usage usage) {
        this.address = address;
        this.usage = usage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Destination that = (Destination) o;
        return address.equals(that.address) && usage == that.usage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, usage);
    }
}
