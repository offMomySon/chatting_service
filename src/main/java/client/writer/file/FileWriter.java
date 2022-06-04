package client.writer.file;

import client.message.file.FileMessage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.NonNull;
import static util.IoUtil.createFileAppender;

public class FileWriter {
    private static final SimpleDateFormat FILE_NAME_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");
    private static final SimpleDateFormat MESSAGE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private final String msgOwner;

    public FileWriter(@NonNull String msgOwner) {
        this.msgOwner = msgOwner;
    }
    public void write(@NonNull FileMessage fileMessage) {
        String message = MessageFormat.format("{0} [{1}] {2}\n", MESSAGE_TIME_FORMAT.format(new Date()), msgOwner, fileMessage.create());
        File file = createFileIfNotExist();

        doWrite(message, createFileAppender(file));
    }

    private File createFileIfNotExist(){
        File file = new File(FILE_NAME_FORMAT.format(new Date()));

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Fail create file. Name = " + file.getName(), e);
            }
        }

        return file;
    }

    private void doWrite(String message, BufferedWriter out){
        try {
            out.write(message);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
