package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import lombok.NonNull;
import static util.IoUtil.createReader;

class Receiver implements Runnable {
    public static final char[] cMsg  = new char[20];

    private final BufferedReader in;

    private Receiver(@NonNull BufferedReader in) {
        this.in = in;
    }

    public static Receiver create(@NonNull InputStream inputStream){
        return new Receiver(createReader(inputStream));
    }

    public void run() {
        while(in !=null) {
            try {
                int count = in.read(cMsg);
                String msg = String.valueOf(cMsg,0, count);

                System.out.println(msg);
            } catch(IOException e) {
                throw new RuntimeException("메세지 수신에 실패했습니다.");
            }
        }
    }
}
