package server.message.smf;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import server.message.file.LogMessage;
import static java.time.LocalDateTime.now;

public abstract class SimpleMessageTest {

    @DisplayName("message 가 null, empty, blank 이면 exception 이 발생합니다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void test1(String message){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(()-> createLogMessage(message));

        //then
        Assertions.assertThat(actual)
            .isNotNull();
    }

    @DisplayName("message 가 정상적인 값이면, exception 이 발생하지 않습니다.")
    @ParameterizedTest
    @ValueSource(strings = {"not blank", " has prefix space."})
    void test2(String message){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(()->createLogMessage(message));

        //then
        Assertions.assertThat(actual)
            .doesNotThrowAnyException();
    }

    @DisplayName("특정 code 를 포함한 메세지를 생성합니다.")
    @ParameterizedTest
    @MethodSource(value = "provideRandomMessage")
    void test3(String message){
        //given
        SimpleMessage simpleMessage = createLogMessage(message);

        //when
        String actual = simpleMessage.create();

        //then
        Assertions.assertThat(actual)
            .contains(getCode());
    }

    protected static Stream<Arguments> provideRandomMessage(){
        List<String> messages = IntStream.range(0, 10)
            .mapToObj(i-> RandomStringUtils.randomAlphanumeric(1, 20))
            .collect(Collectors.toList());

        return messages.stream()
            .map(Arguments::of);
    }

    protected abstract SimpleMessage createLogMessage(String message);

    protected abstract String getCode();
}