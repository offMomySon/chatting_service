package server;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.math.NumberUtils;


/**
 * 특정 ip 가 목적지라는 데이터를 담은 객체
 */
@Getter
public class Address {
    private static final Integer MAX_ADDRESS_NUM = 255;
    private static final Integer MIN_ADDRESS_NUM = 0;
    private static final String DELIMITER = "\\.";
    private static final int IP4_PART_SIZE = 4;

    private final String value;
    public Address(@NonNull String value) {
        this.value = validate(value);
    }

    private static String validate(String address) {
        String[] splitAddress = address.split(DELIMITER);

        if(isNotIp4PartSize(splitAddress)){
            throw new RuntimeException("Invalid address part size.");
        }
        if(isNotNumericAddress(splitAddress)){
            throw new RuntimeException("Invalid address format.");
        }

        if(isNotIpValueRange(splitAddress)){
            throw new RuntimeException("Invalid Address.");
        }

        return address;
    }

    private static boolean isNotIp4PartSize(String[] splitAddress){
        return splitAddress.length != IP4_PART_SIZE;
    }

    private static Boolean isNotNumericAddress(String[] splitAddress){
        return Arrays.stream(splitAddress).anyMatch(eachAddress -> !NumberUtils.isParsable(eachAddress));
    }
    private static boolean isNotIpValueRange(String[] splitAddress){
        return Arrays.stream(splitAddress)
            .map(Integer::parseInt)
            .anyMatch(Address::isNotIpValueRange);
    }

    private static boolean isNotIpValueRange(int address){
        return !(address >= MIN_ADDRESS_NUM && address <= MAX_ADDRESS_NUM);
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
