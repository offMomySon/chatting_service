package server.message.smf.notice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import server.message.smf.SimpleMessage;
import server.message.smf.SimpleMessageTest;
import server.message.smf.generic.GenericSimpleMessage;
import static org.junit.jupiter.api.Assertions.*;

public abstract class NoticePrefixSimpleMessageTest extends SimpleMessageTest {

    @DisplayName("메세지에 notice message 에 prefix 메세지가 포함되어야 합니다.")
    @ParameterizedTest
    @MethodSource("provideRandomMessage")
    void test5(String sMessage){
        //given
        NoticeSimpleMessage message = (NoticeSimpleMessage) createLogMessage(sMessage);

        //when
        String actual = message.create();

        //then
        Assertions.assertThat(actual)
            .contains(getPrefix());
    }



    @Override
    protected String getCode() {
        return "1";
    }

    protected abstract String getPrefix();

}