package server;

import client.format.SimpleMessageFormat;
import client.format.SimpleMessageFormatFactory;
import common.repository.AddressRepository;
import java.io.BufferedReader;
import java.io.IOException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import server.sender.SmfSender;
import server.sender.factory.SmfSendStrategyFactory;
import server.sender.validator.AddressValidator;
import server.type.Cmd;
import static util.IoUtil.*;

/**
 * server 에서 입력 받은 cmd 를 client 에 뿌리는 역할.
 */
@Slf4j
class Sender {
    private static final String STOP_READ = null;
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

                SimpleMessageFormat simpleMessageFormat = simpleMessageFormatFactory.create(cmd);

                SmfSendStrategyFactory smfSendStrategyFactory = new SmfSendStrategyFactory(new AddressValidator(), addressRepository, simpleMessageFormat);

                SmfSender smfSender = smfSendStrategyFactory.create(getDestination(cmd));
                smfSender.send();

//                List<Destination> destinations =destinationFactory.createDestinations(getDestination(cmd, simpleMessageFormat));

//                String address = getAddress(simpleMessageFormat, cmd);
//
//                SMFSender smfSender = new SMFSender(simpleMessageFormat, addressRepository);
//                smfSender.send(address);
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail console read.",e);
        }
    }

    private String getDestination(String scmd) {
        String[] splitCmd = scmd.split(" ");

        Cmd cmd = Cmd.from(splitCmd[0]).orElseThrow(()-> new RuntimeException("일치하는 cmd 가 존재하지 않습니다."));

        switch (cmd){
            case SEND:
                return splitCmd[1];
            case NOTICE:
                return splitCmd[2];
            default:
                throw new RuntimeException("일치하는 cmd 가 존재하지 않습니다.");
        }
    }
}