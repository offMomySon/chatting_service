package server.v5;

import java.util.Objects;
import lombok.NonNull;
import server.Address;

public class AddressDirection {
    private final Address address;
    private final Usage usage;

    public AddressDirection(@NonNull Address address, @NonNull Usage usage) {
        this.address = address;
        this.usage = usage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDirection that = (AddressDirection) o;
        return address.equals(that.address) && usage == that.usage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, usage);
    }
}
