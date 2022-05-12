package server.actor;

import common.SimpleMessageFormat;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import repository.IpOutputStreamRepository;
import server.cmd.Cmd;
import server.consumer.IpSupplier;
import server.domain.IpAddress;

/**
 * smf 메세지를 ip 에 전달하는 역할
 */
public class SMFSender implements Actor {
    private final IpOutputStreamRepository ipRepository;
    private final IpSupplier ipSupplier;

    public SMFSender(@NonNull IpOutputStreamRepository ipRepository, @NonNull IpSupplier ipSupplier) {
        this.ipRepository = ipRepository;
        this.ipSupplier = ipSupplier;
    }

    public void accept(@NonNull Cmd cmd){
        SimpleMessageFormat smf = cmd.createSMF();
        List<BufferedWriter> outs = getBufferedWriters();

        outs.forEach(out -> sendWithSMF(out, smf));
    }

    private List<BufferedWriter> getBufferedWriters(){
        return ipSupplier.supplier().stream()
            .filter(ipRepository::contain)
            .map(ipRepository::get)
            .collect(Collectors.toList());
    }

    private void sendWithSMF(BufferedWriter out, SimpleMessageFormat smf){
        try {
            out.write(smf.createMsg());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Fail message send.", e);
        }
    }
}
