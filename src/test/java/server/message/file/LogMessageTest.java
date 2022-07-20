package server.message.file;

import common.MessageOwner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static common.MessageOwner.INFO;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;

class LogMessageTest {
    private static final DateTimeFormatter MESSAGE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    @DisplayName("message 가 null, empty, blank 이면 exception 이 발생합니다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void test1(String message){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(()-> LogMessage.of(now(), INFO, message));

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
        Throwable actual = Assertions.catchThrowable(()->LogMessage.of(now(), INFO, message));

        //then
        Assertions.assertThat(actual)
            .doesNotThrowAnyException();
    }

    @DisplayName("특정 타임 포멧을 가진 메세지를 생성합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"not blank", " has prefix space."})
    void test3(String message){
        //given
        LocalDateTime now = LocalDateTime.now();
        LogMessage logMessage = LogMessage.of(now,INFO,message);

        //when
        String actual = logMessage.create();

        //then
        Assertions.assertThat(actual)
            .contains(MESSAGE_TIME_FORMATTER.format(now));
    }
}