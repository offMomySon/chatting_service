package server.writer;

import lombok.NonNull;
import server.v5.Message;
import server.v5.Usage;

public interface MessageWriteStrategy {
    void write(@NonNull Usage usage, @NonNull Message message);
}
