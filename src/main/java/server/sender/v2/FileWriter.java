package server.sender.v2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import server.destination.Address;
import util.IoUtil;

public class FileWriter {
    private static final String FILE_PREFIX_NAME = "yyyyMMddHHmm";
    private static final String MESSAGE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private final Set<Address> destination = new HashSet<>();

    public void addAddress(@NonNull Address address){
        destination.add(address);
    }

    public void write(@NonNull String message, @NonNull String owner, @NonNull Collection<Address> addresses){
        String fileMessage = createFileMessage(message, owner);

        addresses.stream()
            .filter(destination::contains)
            .map(this::createFileIfNotExist)
            .map(IoUtil::createFileAppender)
            .forEach(out -> doWrite(fileMessage,out));
    }

    public void writeAll(@NonNull String message, @NonNull String owner){
        List<Address> addresses = destination.stream().collect(Collectors.toUnmodifiableList());

        write(message, owner, addresses);
    }

    private void doWrite(String line, BufferedWriter out) {
        try {
            out.write(line);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String createFileMessage(String message, String owner){
        String timeFormat = new SimpleDateFormat(MESSAGE_TIME_FORMAT).format(new Date());

        return MessageFormat.format("{0} {1} {2}", timeFormat, owner, message);
    }

    @NotNull
    private File createFileIfNotExist(Address address) {
        String fileName = MessageFormat.format("{0}_{1}", new SimpleDateFormat(FILE_PREFIX_NAME).format(new Date()), address.getValue());
        File file = new File(fileName);

        try {
            if(!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Fail to create File", e);
        }
        return file;
    }
}
