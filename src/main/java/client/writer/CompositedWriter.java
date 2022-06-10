package client.writer;

import java.util.List;
import lombok.NonNull;

public class CompositedWriter implements Writer{
    private final List<Writer> writers;

    public CompositedWriter(@NonNull List<Writer> writers) {
        this.writers = writers;
    }

    @Override
    public void write() {
        writers.forEach(Writer::write);
    }
}
