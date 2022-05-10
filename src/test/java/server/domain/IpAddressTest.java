package server.domain;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class IpAddressTest {

    @DisplayName("정상적인 ip foramt 이면 생성됩니다.")
    @ParameterizedTest
    @MethodSource("validIPv4Provider")
    void test1(String ip){
        //givne
        //when
        Throwable actual = Assertions.catchThrowable(()->new IpAddress(ip, isAllMatch));

        //then
        Assertions.assertThat(actual)
            .isNull();
    }

    @DisplayName("비정상적인 ip format 이면 exception 이 발생합니다.")
    @ParameterizedTest
    @MethodSource("invalidIPv4Provider")
    void test2(String ip){
        //givne
        //when
        Throwable actual = Assertions.catchThrowable(()->new IpAddress(ip, isAllMatch));

        //then
        Assertions.assertThat(actual)
            .isNotNull();
    }


    static Stream<String> validIPv4Provider() {
        return Stream.of(
            "0.0.0.0",
            "0.0.0.1",
            "127.0.0.1",
            "192.168.0.2",
            "1.2.3.4",              // 0-9
            "11.1.1.0",             // 10-99
            "101.1.1.0",            // 100-199
            "201.1.1.0",            // 200-249
            "255.255.255.255",      // 250-255
            "192.168.1.1",
            "192.168.1.255",
            "100.100.100.100");
    }

    static Stream<String> invalidIPv4Provider() {
        return Stream.of(
            "000.000.000.000",          // leading 0
            "00.00.00.00",              // leading 0
            "1.2.3.04",                 // leading 0
            "1.02.03.4",                // leading 0
            "1.2",                      // 1 dot
            "1.2.3",                    // 2 dots
            "1.2.3.4.5",                // 4 dots
            "192.168.1.1.1",            // 4 dots
            "256.1.1.1",                // 256
            "1.256.1.1",                // 256
            "1.1.256.1",                // 256
            "1.1.1.256",                // 256
            "-100.1.1.1",               // -100
            "1.-100.1.1",               // -100
            "1.1.-100.1",               // -100
            "1.1.1.-100",               // -100
            "1...1",                    // empty between .
            "1..1",                     // empty between .
            "1.1.1.1.",                 // last .
            "");                        // empty
    }

}