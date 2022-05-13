package server;

import java.io.BufferedReader;
import java.io.IOException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import repository.IpOutputStreamRepository;
import server.actor.FileRecorder;
import server.actor.SMFSender;
import server.consumer.IpSupplier;
import server.cmd.Cmd;
import server.cmd.factory.CmdFactory;
import server.consumer.factory.IpSupplierFactory;
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
    private final IpOutputStreamRepository ipOutputStreamRepository;

    private Sender(IpOutputStreamRepository ipOutputStreamRepository) {
        this.ipOutputStreamRepository = ipOutputStreamRepository;
    }

    public static Sender from(@NonNull IpOutputStreamRepository ipRepository){
        return new Sender(ipRepository);
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

                IpSupplierFactory ipSupplierFactory = new IpSupplierFactory(ipOutputStreamRepository);
                IpSupplier ipSupplier = ipSupplierFactory.create(cmd.getIpAddresses());

                SMFSender smfSender = new SMFSender(ipOutputStreamRepository, ipSupplier);
                smfSender.accept(cmd);

                FileRecorder fileRecorder = new FileRecorder(ipOutputStreamRepository, ipSupplier, "서버");
                fileRecorder.accept(cmd);
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail console read.",e);
        }
    }

    @NotNull
    private String removeInitiateCmd(String sCmd) {
        return sCmd.substring(1);
    }
}