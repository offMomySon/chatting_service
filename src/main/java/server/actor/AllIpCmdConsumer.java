package server.actor;


import java.util.function.BiConsumer;
import lombok.NonNull;
import repository.IpOutputStreamRepository;
import server.cmd.Cmd;
import server.domain.IpAddress;

/**
 * 모든 ip 들에 대해 cmd 를 consume 하는 역할.
 */
public class AllIpCmdConsumer {
    private final IpOutputStreamRepository ipRepository;
    private final BiConsumer<IpAddress, Cmd> consumer;

    public AllIpCmdConsumer(@NonNull IpOutputStreamRepository ipRepository, @NonNull BiConsumer<IpAddress, Cmd> consumer) {
        this.ipRepository = ipRepository;
        this.consumer = consumer;
    }

    public void appect(Cmd cmd){
        consumeAll(cmd);
    }

    private void consumeAll(Cmd cmd) {
        ipRepository.getKeySet().stream()
            .forEach(ipAddress -> consumer.accept(ipAddress, cmd));
    }
}
