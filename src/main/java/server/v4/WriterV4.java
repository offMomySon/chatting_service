package server.v4;

import lombok.NonNull;

public interface WriterV4 {
    void write(@NonNull String message);
}
