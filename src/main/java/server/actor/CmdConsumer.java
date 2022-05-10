package server.actor;

import common.SimpleMessageFormat;
import java.io.BufferedWriter;
import java.util.List;
import java.util.function.BiConsumer;
import lombok.NonNull;
import repository.IpOutputStreamRepository;
import server.domain.IpAddress;
import server.domain.IpAddresses;
import server.cmd.Cmd;

/**
 * ipAddresses 의 ipRepository 포함여부에 따라 cmd 를 소비하는 역할.
 */
public class CmdConsumer {
    private final IpOutputStreamRepository ipRepository;
    private final BiConsumer<BufferedWriter, SimpleMessageFormat> consumer;

    public CmdConsumer(@NonNull IpOutputStreamRepository ipRepository, @NonNull BiConsumer<BufferedWriter, SimpleMessageFormat> consumer) {
        this.ipRepository = ipRepository;
        this.consumer = consumer;
    }

    public void appect(IpAddresses ipAddresses, SimpleMessageFormat smf){
        if(ipAddresses.isAllUser()){
            consumeAll(smf);
            return;
        }

        consumeSpecificIp(ipAddresses.getIps(), smf);
    }

    private void consumeSpecificIp(List<IpAddress> requestIp, SimpleMessageFormat smf){
        requestIp.stream()
            .filter(ipRepository::contain)
            .map(ipRepository::get)
            .forEach(out -> consumer.accept(out, smf));
    }

    private void consumeAll(SimpleMessageFormat smf) {
        ipRepository.values().stream()
            .forEach(out -> consumer.accept(out, smf));
    }
}
