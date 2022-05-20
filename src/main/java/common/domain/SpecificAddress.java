package common.domain;

import java.util.Arrays;


/**
 * 특정 ip 가 목적지라는 데이터를 담은 객체
 */
public class SpecificAddress extends Address {
    private static final Integer MAX_ADDRESS_NUM = 255;

    public SpecificAddress(String value) {
        super(validate(value));
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
}
