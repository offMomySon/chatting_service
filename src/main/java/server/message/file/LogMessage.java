package server.message.file;

import common.Subject;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import server.v5.Message;
import static common.Subject.WARN;

/**
 * 역할.
 * 타임스탬프를 남기는 로그형 메세지를 생성합니다.(=> view 역할)
 *
 * LocalDateTime, MessageOwner, String 은 value(= message) 만들어주기 위해서 필요하다.
 * (다른 위치에서 사용되지 않는다.)
 * 그래서 instance variable 로 value 만을 남겼다.
 */
public abstract class LogMessage implements Message {
    private final String value;

    protected LogMessage(String value) {
        this.value = validate(value);
    }

    protected static String validate(String message){
        if(Objects.isNull(message)){
            throw new RuntimeException("message is null.");
        }
        if(StringUtils.isBlank(message)){
            throw new RuntimeException("message is blank.");
        }
        if(StringUtils.isEmpty(message)){
            throw new RuntimeException("message is empty.");
        }
        return message;
    }

    @Override
    public String create() {
        return value;
    }
}
