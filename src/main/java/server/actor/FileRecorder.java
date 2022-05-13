package server.actor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import repository.IpOutputStreamRepository;
import server.cmd.Cmd;
import server.consumer.IpSupplier;
import server.domain.IpAddress;
import static util.IoUtil.createFileAppender;

public class FileRecorder implements Actor{
    private final IpOutputStreamRepository ipRepository;
    private final IpSupplier ipSupplier;
    private final String writer;

    public FileRecorder(@NonNull IpOutputStreamRepository ipRepository, IpSupplier ipSupplier, String writer) {
        this.ipRepository = ipRepository;
        this.ipSupplier = ipSupplier;
        this.writer = writer;
    }

    @Override
    public void accept(@NonNull Cmd cmd) {
        Date now = new Date();
        String yyyyMMddHHmm = new SimpleDateFormat("yyyyMMddHHmm").format(now);
        List<IpAddress> ipAddresses = getIpAddresses();

        ipAddresses.stream()
            .map(ipAddress -> new File(yyyyMMddHHmm + "_" + ipAddress.getValue()))
            .peek(this::createFileIfNotExist)
            .forEach(file -> writeWithFile(file, cmd.getMessage(), now));
    }

    public void accept(@NonNull String message){
        Date now = new Date();
        String yyyyMMddHHmm = new SimpleDateFormat("yyyyMMddHHmm").format(now);
        List<IpAddress> ipAddresses = getIpAddresses();

        ipAddresses.stream()
            .map(ipAddress -> new File(yyyyMMddHHmm + "_" + ipAddress.getValue()))
            .peek(this::createFileIfNotExist)
            .forEach(file -> writeWithFile(file, message, now));
    }

    private void writeWithFile(File file, @NonNull String msg, Date now){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        StringBuilder log = new StringBuilder();
        log.append(dateFormat.format(now)).append(" ").append("[").append(writer).append("]").append(" ").append(msg).append("\n");

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

    private List<IpAddress> getIpAddresses() {
        return ipSupplier.supplier().stream()
            .filter(ipRepository::contain)
            .collect(Collectors.toList());
    }

}
