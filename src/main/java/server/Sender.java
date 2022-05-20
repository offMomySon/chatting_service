package server;

import common.protocol.NoticeSimpleMessageFormat;
import common.protocol.SimpleMessageFormat;
import common.protocol.SimpleMessageFormatFactory;
import common.repository.AddressRepository;
import java.io.BufferedReader;
import java.io.IOException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import server.actor.SMFSender;
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

    private static final CompositeCmdValidator cmdValidator = CompositeCmdValidator.from(new GeneralCmdValidator(), new NoticeCmdValidator());
    private static final SimpleMessageFormatFactory simpleMessageFormatFactory = new SimpleMessageFormatFactory();
    private static final BufferedReader in = createReader(System.in);

    private final AddressRepository addressRepository;

    public Sender(@NonNull AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void waitAndThenSendMsg() {
        String cmd;
        try{
            while( (cmd = in.readLine()) != STOP_READ){
                log.info("console write : {}", cmd);

                CmdValidateResult validateResult = cmdValidator.validate(cmd);
                if(validateResult.notValid()){
                    log.info("Invalid cmd. '{}'", validateResult.getMsg());
                    continue;
                }

                SimpleMessageFormat simpleMessageFormat = simpleMessageFormatFactory.create(cmd);



                String address = getAddress(simpleMessageFormat, cmd);

                SMFSender smfSender = new SMFSender(simpleMessageFormat, addressRepository);
                smfSender.send(address);
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail console read.",e);
        }
    }

    private String getAddress(SimpleMessageFormat simpleMessageFormat, String cmd) {
        String[] cmds = cmd.split(" ");

        if( simpleMessageFormat instanceof NoticeSimpleMessageFormat){
            return cmds[2];
        }
        return cmds[1];
    }
}