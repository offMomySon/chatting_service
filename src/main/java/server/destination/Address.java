package server.destination;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;


/**
 * 특정 ip 가 목적지라는 데이터를 담은 객체
 */
@Getter
public class Address {
    private static final Integer MAX_ADDRESS_NUM = 255;

    private final String value;
    public Address(String value) {
        this.value = validate(value);
    }

    private static String validate(String address) {
        if(checkValidAddress(address)){
            throw new RuntimeException("Invalid Address.");
        }
        return address;
    }

    private static boolean checkValidAddress(String address){
        return Arrays.stream(address.split("."))
            .map(Integer::parseInt)
            .anyMatch(addressNum -> addressNum > MAX_ADDRESS_NUM);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(value, address.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
