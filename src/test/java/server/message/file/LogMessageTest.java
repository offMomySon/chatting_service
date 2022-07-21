package server.message.file;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
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
import static java.time.LocalDateTime.now;

abstract class LogMessageTest {

    @DisplayName("message 가 null, empty, blank 이면 exception 이 발생합니다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void test1(String message){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(()-> createLogMessage(now(), message));

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
        Throwable actual = Assertions.catchThrowable(()->createLogMessage(now(), message));

        //then
        Assertions.assertThat(actual)
            .doesNotThrowAnyException();
    }

    @DisplayName("특정 타임 포멧을 가진 메세지를 생성합니다.")
    @ParameterizedTest
    @MethodSource(value = "provideRandomMessage")
    void test3(String message){
        //given
        System.out.println("##1");
        DateTimeFormatter MESSAGE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

        LocalDateTime now = LocalDateTime.now();
        LogMessage logMessage = createLogMessage(now,message);

        //when
        String actual = logMessage.create();

        //then
        Assertions.assertThat(actual)
            .contains(MESSAGE_TIME_FORMATTER.format(now));
    }

    @DisplayName("특정 prefix 를 포함한 메세지를 생성합니다.")
    @ParameterizedTest
    @MethodSource(value = "provideRandomMessage")
    void test4(String message){
        //given
        System.out.println("##2");
        LogMessage logMessage = createLogMessage(LocalDateTime.now(), message);

        //when
        String actual = logMessage.create();

        //then
        Assertions.assertThat(actual)
            .contains(getPrefix());
    }


    private static Stream<Arguments> provideRandomMessage(){
        List<String> messages = IntStream.range(0, 10)
            .mapToObj(i-> RandomStringUtils.randomAlphanumeric(1,20))
            .collect(Collectors.toList());

        return messages.stream()
            .map(Arguments::of);
    }

    protected abstract LogMessage createLogMessage(LocalDateTime dateTime, String message);

    protected abstract String getPrefix();
}