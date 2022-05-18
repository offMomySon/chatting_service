package server.domain;

import java.util.Arrays;
import java.util.Objects;
import lombok.NonNull;


/**
 * ip address 를 담는 역할.
 */
public class Address {
    private static final Integer MAX_ADDRESS_NUM = 255;

    private final String value;

    public Address(@NonNull String value) {
        this.value = validate(value);
    }

    private String validate(String address) {
        if(isValidAddress(address)){
            throw new RuntimeException("Invalid Address.");
        }
        return address;
    }

    private boolean isValidAddress(String address){
        return Arrays.stream(address.split("."))
            .map(Integer::parseInt)
            .anyMatch(addressNum -> addressNum > MAX_ADDRESS_NUM);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return value.equals(address.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
