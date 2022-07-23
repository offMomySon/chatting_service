package server.message.file;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import static common.Subject.INFO;
import static common.Subject.SERVER;
import static common.Subject.WARN;

/**
 * 역할.
 * 서버 포멧의 로그 메세지를 생성하는 역할.
 *
 * 구현 장/단 비교.
 * 1) LogMessage.ofServer(LocalDateTime, String) : LogMessage
 * 2) LogServerMessage.of(LocalDateTime, String) : LogServerMessage
 *
 * 장.
 * 1) class 로 메세지가 어떤 형태인지 알 수 있다.
 * 단.
 * 1) 형이 추가 되면, class 를 만들어야한다. <-> static method 를 만들어야한다. ( 비등? )
 * 2) 유지보수 측면? - 포멧 변경이 발생. -> 특정 클래스 수정 <-> LogMessage static method 수정.(class 로 분할해야할 수도) ( 비등? )
 */
public class LogServerMessage extends LogMessage{
    private static final DateTimeFormatter MESSAGE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private LogServerMessage(@NonNull String value) {
        super(validate(value));
    }

    private static LogServerMessage of(LocalDateTime dateTime, String message){
        String formedMessage = MessageFormat.format("{0} {1}", MESSAGE_TIME_FORMATTER.format(dateTime), SERVER.with(validate(message)));
        return new LogServerMessage(formedMessage);
    }

    public static LogServerMessage ofCurrent(String message){
        return of(LocalDateTime.now(), message);
    }
}
