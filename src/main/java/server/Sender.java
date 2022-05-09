package server;

import common.SimpleMessageFormat;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import repository.IpOutputStreamRepository;
import server.actor.CmdConsumer;
import server.cmd.Cmd;
import server.cmd.NoticeCmd;
import server.cmd.factory.CompositeCmdCmdFactory;
import server.cmd.factory.GeneralCmdCmdFactory;
import server.cmd.factory.NoticeCmdCmdFactory;
import server.domain.IpAddress;
import server.domain.IpAddresses;
import server.validate.CmdValidateResult;
import server.validate.CompositeCmdValidator;
import server.validate.GeneralCmdValidator;
import server.validate.NoticeCmdValidator;
import static util.IoUtil.*;

/**
 * server 에서 입력 받은 cmd 를 client 에 뿌리는 역할.
 */
@Slf4j
class Sender {
    private static final String STOP_READ = null;

    private final CompositeCmdValidator cmdValidator = CompositeCmdValidator.from(new GeneralCmdValidator(), new NoticeCmdValidator());
    private final CompositeCmdCmdFactory cmdFactory = new CompositeCmdCmdFactory(List.of(new GeneralCmdCmdFactory(), new NoticeCmdCmdFactory()));
    private final BufferedReader in = createReader(System.in);
    private final CmdConsumer msgSender;

    private Sender(@NonNull CmdConsumer msgSender) {
        this.msgSender = msgSender;
    }

    public static Sender from(@NonNull IpOutputStreamRepository ipRepository){
        return new Sender(new CmdConsumer(ipRepository, cmdWriter));
    }

    public void waitAndThenSendMsg() {
        String sCmd;
        try{
            while( (sCmd = in.readLine()) != STOP_READ){
                log.info("console write : {}", sCmd);

                CmdValidateResult validateResult = cmdValidator.validate(sCmd);
                if(validateResult.notValid()){
                    log.info("Invalid cmd. '{}'", validateResult.getMsg());
                    continue;
                }
                sCmd = sCmd.substring(1);

                Cmd cmd = cmdFactory.create(sCmd);
                IpAddresses ipAddresses = getIpAddresses(sCmd, cmd);

                msgSender.appect(ipAddresses, cmd);
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail console read.",e);
        }
    }

    @NotNull
    private IpAddresses getIpAddresses(String sCmd, Cmd cmd) {
        String[] cmds = sCmd.split(" ");

        if(cmd instanceof NoticeCmd){
            List<IpAddress> ipAddresses = Arrays.stream(cmds[2].split(","))
                .map(IpAddress::new)
                .collect(Collectors.toList());

            return new IpAddresses(ipAddresses,true);
        }

        List<IpAddress> ipAddresses = Arrays.stream(cmds[1].split(","))
            .map(IpAddress::new)
            .collect(Collectors.toList());

        return new IpAddresses(ipAddresses,true);
    }

    private static BiConsumer<BufferedWriter, Cmd> cmdWriter = (out, cmd)->{
        SimpleMessageFormat smf = cmd.createSMF();

        try {
            out.write(smf.createMsg());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Fail message send.", e);
        }
    };
}