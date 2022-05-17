package server.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimpleMessageFormatValidatorTest {

    @Test
    void test(){
        String IP_ALL = "^(all)|(\\*)";
        Pattern pattern = Pattern.compile(IP_ALL);

        String test = "all";

        Matcher matcher = pattern.matcher(test);

        Assertions.assertThat(matcher.matches()).isTrue();
    }
}