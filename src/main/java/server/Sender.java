package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import repository.IpOutputStreamRepository;
import server.actor.CmdConsumer;
import server.cmd.Cmd;
import server.cmd.NoticeCmd;
import server.cmd.factory.CompositeCmdFactory;
import server.cmd.factory.GeneralCmdFactory;
import server.cmd.factory.NoticeCmdFactory;

/**
 * server 에서 입력 받은 cmd 를 client 에 뿌리는 역할.
 */
class Sender implements Runnable {
    private final CompositeCmdFactory cmdFactory = new CompositeCmdFactory(List.of(new GeneralCmdFactory(), new NoticeCmdFactory()));
    private final Scanner scanner = new Scanner(System.in);
    private final CmdConsumer msgSender;

    private Sender(@NonNull CmdConsumer msgSender) {
        this.msgSender = msgSender;
    }

    public static Sender from(@NonNull IpOutputStreamRepository ipRepository){
        return new Sender(new CmdConsumer(ipRepository, cmdWriter));
    }

    public void run() {
        String sCmd;
        while( (sCmd = scanner.nextLine()) != null ){
            System.out.println("console write : " + sCmd);

            Cmd cmd = cmdFactory.create(sCmd);
            IpAddresses ipAddresses = getIpAddresses(sCmd, cmd);

            msgSender.appect(ipAddresses, cmd);
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

    private static BiConsumer<BufferedWriter, Cmd> cmdWriter = (out, cmd)->{
        try {
            out.write(cmd.createSMF());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("메세지 전송에 실패했습니다. ", e);
        }
    };
}