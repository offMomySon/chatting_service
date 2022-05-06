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
import org.jetbrains.annotations.NotNull;
import repository.IpOutputStreamRepository;
import server.cmd.Cmd;
import server.cmd.GeneralCmd;
import server.cmd.NoticeCmd;
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
        String sCmd;
        while( (sCmd = scanner.nextLine()) != null ){
            System.out.println("console write : " + sCmd);

            Cmd cmd = cmdFactory.create(sCmd);
            IpAddresses ipAddresses = getIpAddresses(sCmd, cmd);

            sendMsg(ipAddresses,cmd);
        }
    }

    @NotNull
    private IpAddresses getIpAddresses(String sCmd, Cmd cmd) {
        String[] cmds = sCmd.split(" ");

        if(cmd instanceof NoticeCmd){
            return IpAddresses.from(cmds[2].split(","));
        }

        return IpAddresses.from(cmds[1].split(","));
    }

    private void sendMsg(IpAddresses ipAddresses, Cmd cmd){
        if(ipAddresses.isAllUser()){
            sendToAll(cmd);
        }
    }

    private void sendToAll(Cmd cmd) {
        Collection<BufferedWriter> values = ipRepository.values();

        values.stream().forEach(out -> {
            try {
                out.write(cmd.createSMF());
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException("메세지 전송에 실패했습니다. ", e);
            }
        });
    }
}