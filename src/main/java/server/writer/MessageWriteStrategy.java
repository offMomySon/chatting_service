package server.writer;

import lombok.NonNull;
import server.v5.Message;
import server.v5.Usage;

/**
 * 역할.
 * 클라이언트 식별자(*,ips) 에 따른 message 전송방식을 추상화 하는 역할.
 */
public interface MessageWriteStrategy {
    void write(@NonNull Message message);
}
