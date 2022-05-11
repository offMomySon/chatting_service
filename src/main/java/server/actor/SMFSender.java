package server.actor;

import common.SimpleMessageFormat;
import java.io.BufferedWriter;
import java.io.IOException;
import lombok.NonNull;
import repository.IpOutputStreamRepository;
import server.cmd.Cmd;
import server.domain.IpAddress;

/**
 * smf 메세지를 ip 에 전달하는 역할
 */
public class SMFSender implements Actor{
    private final IpOutputStreamRepository ipRepository;

    public SMFSender(@NonNull IpOutputStreamRepository ipRepository) {
        this.ipRepository = ipRepository;
    }

    public void accept(IpAddress ipAddress, Cmd cmd){
        BufferedWriter out = ipRepository.get(ipAddress);
        SimpleMessageFormat smf = cmd.createSMF();

        sendWithSMF(out, smf);
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
