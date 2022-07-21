package common;

import java.time.LocalDateTime;
import java.util.Random;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class SubjectTest {

    @DisplayName("올바르지 않은 메세지를 받으면 exception 이 발생합니다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void test1(String message){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(()->createRandomSubject().with(LocalDateTime.now(), message) );

        //then
        Assertions.assertThat(actual)
            .isNotNull();
    }

    @DisplayName("각각의 Enum 에서 특정 형식의 String 을 생성합니다.")
    @ParameterizedTest
    @EnumSource(value = Subject.class)
    void test(Subject subject){
        //given
        //when
        String actual = subject.with(LocalDateTime.now(), "message");

        //then
        Assertions.assertThat(actual)
            .contains(subject.getValue());
    }

    private static Subject createRandomSubject(){
        Random random = new Random();
        Subject[] values = Subject.values();

        return values[random.nextInt(values.length)];
    }
}