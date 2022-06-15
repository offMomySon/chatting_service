package common;

import java.io.BufferedWriter;
import java.io.IOException;
import lombok.NonNull;

public class Writer {
    private final BufferedWriter out;

    public Writer(@NonNull BufferedWriter out) {
        this.out = out;
    }

    public void write(@NonNull String message){
        try{
            out.write(message);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("bufferedWriter wirte 에 실패했습니다.", e);
        }
    }
}
