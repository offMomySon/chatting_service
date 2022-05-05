package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Scanner;
import lombok.NonNull;
import static util.IoUtil.createWriter;

class Sender implements Runnable {
    private final BufferedWriter out;

    private Sender(@NonNull BufferedWriter out) {
        this.out = out;
    }

    public static Sender create(@NonNull OutputStream outputStream){
        return new Sender(createWriter(outputStream));
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        try {
            while(out !=null) {
                String line = scanner.nextLine();
                System.out.println("enter msg = " + line);
                out.write(line);
                out.flush();
            }
        } catch(IOException e) {
            throw new RuntimeException("메세지 전송에 실패했습니다.");
        }
    }
}
