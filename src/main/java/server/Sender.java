package server;

import common.SimpleMessageFormat;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.function.BiConsumer;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import repository.IpOutputStreamRepository;
import server.actor.CmdConsumer;
import server.cmd.Cmd;
import server.cmd.factory.CmdFactory;
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
    private final CmdFactory cmdFactory = new CmdFactory();
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
                sCmd = removeInitiateCmd(sCmd);

                Cmd cmd = cmdFactory.create(sCmd);

                IpAddresses ipAddresses = new IpAddresses(cmd.getIpAddresses(), true);
                msgSender.appect(ipAddresses, cmd.createSMF());
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail console read.",e);
        }
    }

    @NotNull
    private String removeInitiateCmd(String sCmd) {
        return sCmd.substring(1);
    }

    private static BiConsumer<BufferedWriter, SimpleMessageFormat> cmdWriter = (out, smf)->{
        try {
            out.write(smf.createMsg());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Fail message send.", e);
        }
    };
}