package server.message.file;

import common.Subject;
import java.time.LocalDateTime;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

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
    private LogServerMessage(@NonNull String value) {
        super(value);
    }

    public static LogServerMessage of(@NonNull LocalDateTime dateTime, @NonNull String message){
        if(StringUtils.isBlank(message)){
            throw new RuntimeException("message is blank.");
        }
        if(StringUtils.isEmpty(message)){
            throw new RuntimeException("message is empty.");
        }

        return new LogServerMessage(Subject.SERVER.with(dateTime, message));
    }
}
