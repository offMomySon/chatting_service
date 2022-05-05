package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.NonNull;
import repository.IpOutputStreamRepository;
import static util.IoUtil.createWriter;

class Sender implements Runnable {
    private final BufferedWriter out;
    private final IpOutputStreamRepository ipRepository;

    private Sender(@NonNull BufferedWriter out, @NonNull IpOutputStreamRepository ipRepository) {
        this.out = out;
        this.ipRepository = ipRepository;
    }

    public static Sender create(@NonNull OutputStream outputStream, @NonNull IpOutputStreamRepository ipRepository) {
        return new Sender(createWriter(outputStream), ipRepository);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String msg;
        while( (msg = scanner.nextLine()) != null ){
            System.out.println("console write : " + msg);

//                out.write("[server]" + line);
//                out.flush();
            sendToAll(msg);
        }
    }

    private void sendToAll(String msg) {
        Collection<BufferedWriter> values = ipRepository.values();

        values.stream().forEach(out -> {
            try {
                out.write(msg);
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException("메세지 전송에 실패했습니다. ", e);
            }
        });
    }
}