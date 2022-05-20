package common.domain;

import java.text.MessageFormat;
import java.util.List;

/**
 * 모든 address 가 목적지 라는 개념을 담은 객체.
 */
public class AllAddress extends Address {
    private static final List<String> ALL_MATCHED = List.of("*", "all");

    protected AllAddress(String value) {
        super(validate(value));
    }

    private static String validate(String value){
        if(ALL_MATCHED.contains(value)){
            throw new RuntimeException(MessageFormat.format("Invalid value, value = {}", value));
        }
        return value;
    }
}
