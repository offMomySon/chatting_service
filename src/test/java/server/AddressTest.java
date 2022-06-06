package server;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AddressTest {

    @DisplayName("IP4 포멧의 주소로 생성할 수 있습니다.")
    @ParameterizedTest
    @ValueSource(strings = {"0.0.0.0", "127.0.0.1", "255.255.255.255"})
    void createAddressTest(String ipv4){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(() -> new Address(ipv4));

        //then
        Assertions.assertThat(actual)
            .isNull();
    }

    @DisplayName("address 가 4분할이 아니면, exception 이 발생합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"1.2.3", "1.2.3.4.5", "1.2.3.4.5.6", "1.2.3.4.5.6.7"})
    void invalidIfAddressIsNotFourPart(String ipv4){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(()-> new Address(ipv4));

        //then
        Assertions.assertThat(actual)
            .isNotNull();
    }

    @DisplayName("address 의 값이 숫자가 아니면, execption 이 발생합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"가.1.2.3", "ㄱ.1.2.3", "a.1.2.3", "b.1.2.3", "1.abc.2.3", "1.2.abc.3", "1.2.3.abc", "a.b.c.d" })
    void invalidIfAddressIsNotNumeric(String ipv4){
        //given
        //when
        Throwable actual =  Assertions.catchThrowable(()-> new Address(ipv4));

        //then
        Assertions.assertThat(actual)
            .isNotNull();
    }

    @DisplayName("address 의 값이 ip4 포멧인 0~255 사이에 존재하지 않으면, exception 이 발생합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"1.2.3.256", "1.2.256.3", "1.256.2.3", "256.1.2.3", "256.256.256.256", "-1.1.2.3"})
    void invalidIfAddressIsNotInIp4Value(String ipv4){
        //given
        //when
        Throwable actual = Assertions.catchThrowable(()->new Address(ipv4));

        //then
        Assertions.assertThat(actual)
            .isNotNull();
    }
}