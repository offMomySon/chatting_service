package server;

import common.view.format.GenericSimpleMessageFormat;
import common.view.format.NoticeSimpleMessageFormat;
import common.view.format.SimpleMessageFormat;
import common.view.format.SimpleMessageFormatFactory;
import common.repository.AddressRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import server.actor.SMFSender;
import server.destination.Destination;
import server.destination.factory.AddressFactory;
import server.destination.factory.CompositedDestinationFactory;
import server.destination.factory.URLFactory;
import static util.IoUtil.*;

/**
 * server 에서 입력 받은 cmd 를 client 에 뿌리는 역할.
 */
@Slf4j
class Sender {
    private static final String STOP_READ = null;

    private static final CompositedDestinationFactory destinationFactory = CompositedDestinationFactory.from(new AddressFactory(), new URLFactory());
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

                /**
                 * anti pattern.
                 */
//                CmdValidateResult validateResult = cmdValidator.validate(cmd);
//                if(validateResult.notValid()){
//                    log.info("Invalid cmd. '{}'", validateResult.getMsg());
//                    continue;
//                }

                SimpleMessageFormat simpleMessageFormat = simpleMessageFormatFactory.create(cmd);

                List<Destination> destinations =destinationFactory.createDestinations(getDestination(cmd, simpleMessageFormat));

//                String address = getAddress(simpleMessageFormat, cmd);
//
//                SMFSender smfSender = new SMFSender(simpleMessageFormat, addressRepository);
//                smfSender.send(address);
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail console read.",e);
        }
    }

    private String getDestination(String cmd, SimpleMessageFormat simpleMessageFormat) {
        String[] sCmd = cmd.split(" ");

        if(simpleMessageFormat instanceof GenericSimpleMessageFormat){
            return sCmd[1];
        }

        if(simpleMessageFormat instanceof NoticeSimpleMessageFormat){
            return sCmd[2];
        }

        throw new RuntimeException("적합한 simpleMessage format 이 존재하지 않습니다.");ㄴ
    }

    private String getAddress(SimpleMessageFormat simpleMessageFormat, String cmd) {
        String[] cmds = cmd.split(" ");

        if( simpleMessageFormat instanceof NoticeSimpleMessageFormat){
            return cmds[2];
        }
        return cmds[1];
    }
}