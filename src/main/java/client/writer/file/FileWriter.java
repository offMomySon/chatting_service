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
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private final BufferedWriter out;
    private final String msgOwner;

    private FileWriter(@NonNull BufferedWriter out, @NonNull String msgOwner) {
        this.out = out;
        this.msgOwner = msgOwner;
    }

    public static FileWriter create(String msgOwner) {
        File file = new File(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Fail create file. Name = " + file.getName(), e);
            }
        }

        return new FileWriter(createFileAppender(file), msgOwner);
    }

    public void write(@NonNull FileMessage fileMessage) {
        String message = MessageFormat.format("{0} [{1}] {2}\n", dateFormat.format(new Date()), msgOwner, fileMessage.create());

        try {
            out.write(message);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
