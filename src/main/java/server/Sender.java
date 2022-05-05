package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import lombok.NonNull;
import repository.IpOutputStreamRepository;
import server.cmd.Cmd;
import server.cmd.factory.CompositeCmdFactory;
import server.cmd.factory.GeneralCmdFactory;
import server.cmd.factory.NoticeCmdFactory;
import static util.IoUtil.createWriter;

class Sender implements Runnable {
    private final IpOutputStreamRepository ipRepository;

    public Sender(@NonNull IpOutputStreamRepository ipRepository) {
        this.ipRepository = ipRepository;
    }

    public void run() {
        CompositeCmdFactory cmdFactory = new CompositeCmdFactory(List.of(new GeneralCmdFactory(), new NoticeCmdFactory()));

        Scanner scanner = new Scanner(System.in);
        String msg;
        while( (msg = scanner.nextLine()) != null ){
            System.out.println("console write : " + msg);

            Cmd cmd = cmdFactory.create(msg);

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