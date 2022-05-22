package server.destination.url;

import java.text.MessageFormat;
import java.util.Objects;
import lombok.NonNull;
import server.destination.Destination;

public class Url implements Destination {
    private static final String URL_PREFIX = "http://";

    private String value;

    public Url(@NonNull String value) {
        this.value = validate(value);
    }

    private static String validate(String value){
        if(Objects.isNull(value)){
            throw new RuntimeException("value is null.");
        }

        if(isContainPrefix(value)){
            throw new RuntimeException(MessageFormat.format("Fail to create URL, value = {}",value));
        }

        return value;
    }

    private static boolean isContainPrefix(String des){
        return des.indexOf(URL_PREFIX) == 0;
    }
}
