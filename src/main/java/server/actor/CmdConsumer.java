package server.actor;

import java.io.BufferedWriter;
import java.util.List;
import java.util.function.BiConsumer;
import lombok.NonNull;
import repository.IpOutputStreamRepository;
import server.IpAddresses;
import server.cmd.Cmd;

/**
 * ipAddresses 의 ipRepository 포함여부에 따라 cmd 를 소비하는 역할.
 */
public class CmdConsumer {
    private final IpOutputStreamRepository ipRepository;
    private final BiConsumer<BufferedWriter, Cmd> consumer;

    public CmdConsumer(@NonNull IpOutputStreamRepository ipRepository, @NonNull BiConsumer<BufferedWriter, Cmd> consumer) {
        this.ipRepository = ipRepository;
        this.consumer = consumer;
    }

    public void appect(IpAddresses ipAddresses, Cmd cmd){
        if(ipAddresses.isAllUser()){
            consumeAll(cmd);
            return;
        }

        consumeSpecificIp(ipAddresses.getIps(), cmd);
    }

    private void consumeSpecificIp(List<String> requestIp, Cmd cmd){
        requestIp.stream()
            .filter(ipRepository::contain)
            .map(ipRepository::get)
            .forEach(out -> consumer.accept(out, cmd));
    }

    private void consumeAll(Cmd cmd) {
        ipRepository.values().stream()
            .forEach(out -> consumer.accept(out, cmd));
    }
}
