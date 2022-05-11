package server.actor;

import java.util.List;
import java.util.function.BiConsumer;
import lombok.NonNull;
import repository.IpOutputStreamRepository;
import server.domain.IpAddress;
import server.cmd.Cmd;

/**
 * specific Ip 에 대해 cmd 를 consume 하는 역할.
 */
public class SpecificIpCmdConsumer {
    private final IpOutputStreamRepository ipRepository;
    private final BiConsumer<IpAddress, Cmd> consumer;

    public SpecificIpCmdConsumer(@NonNull IpOutputStreamRepository ipRepository, @NonNull BiConsumer<IpAddress, Cmd> consumer) {
        this.ipRepository = ipRepository;
        this.consumer = consumer;
    }

    public void appect(Cmd cmd){
        List<IpAddress> ipAddresses = cmd.getIpAddresses();

        consumeSpecificIp(ipAddresses, cmd);
    }

    private void consumeSpecificIp(List<IpAddress> requestIp, Cmd cmd){


        requestIp.stream()
            .filter(ipRepository::contain)
            .forEach(ipAddress -> consumer.accept(ipAddress, cmd));
    }
}
