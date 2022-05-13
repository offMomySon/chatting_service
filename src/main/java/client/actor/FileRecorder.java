package client.actor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import repository.IpOutputStreamRepository;
import server.actor.Actor;
import server.cmd.Cmd;
import server.consumer.IpSupplier;
import server.domain.IpAddress;
import static util.IoUtil.createFileAppender;

public class FileRecorder implements Actor {
    private final IpOutputStreamRepository ipRepository;
    private final IpSupplier ipSupplier;

    public FileRecorder(@NonNull IpOutputStreamRepository ipRepository, IpSupplier ipSupplier) {
        this.ipRepository = ipRepository;
        this.ipSupplier = ipSupplier;
    }

    @Override
    public void accept(@NonNull Cmd cmd) {
        Date now = new Date();
        String yyyyMMddHHmm = new SimpleDateFormat("yyyyMMddHHmm").format(now);

        File file = new File(yyyyMMddHHmm);
        createFileIfNotExist(file);
        writeWithFile(file,cmd.getMessage(),now);
    }

    private void writeWithFile(File file, @NonNull String msg, Date now){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        StringBuilder log = new StringBuilder();
        log.append(dateFormat.format(now)).append(" ").append("[서버]").append(" ").append(msg).append("\n");

        BufferedWriter out = createFileAppender(file);

        try {
            out.write(log.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFileIfNotExist(File file){
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Fail create file. Name = " + file.getName(), e);
            }
        }
    }
}
