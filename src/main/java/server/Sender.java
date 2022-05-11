package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.BiConsumer;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import repository.IpOutputStreamRepository;
import server.consumer.SpecificIpCmdConsumer;
import server.cmd.Cmd;
import server.cmd.factory.CmdFactory;
import server.domain.IpAddress;
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
    private final SpecificIpCmdConsumer msgSender;

    private Sender(@NonNull SpecificIpCmdConsumer msgSender) {
        this.msgSender = msgSender;
    }

    public static Sender from(@NonNull IpOutputStreamRepository ipRepository){
        return new Sender(new SpecificIpCmdConsumer(ipRepository, cmdWriter));
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



                msgSender.accept(cmd);
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail console read.",e);
        }
    }

    @NotNull
    private String removeInitiateCmd(String sCmd) {
        return sCmd.substring(1);
    }

    private static BiConsumer<IpAddress, Cmd> cmdWriter = (out, cmd)->{
//        SimpleMessageFormat smf = cmd.createSMF();
//
//        try {
//            out.write(smf.createMsg());
//            out.flush();
//        } catch (IOException e) {
//            throw new RuntimeException("Fail message send.", e);
//        }
    };
}