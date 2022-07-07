package client.writer.file;

import client.message.file.FileMessage;
import common.Writer;
import java.io.BufferedWriter;
import java.io.File;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import util.IoUtil;

public class BasicFileWriter implements FileWriter {
    private static final DateTimeFormatter FILE_NAME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    private static final DateTimeFormatter MESSAGE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private final Writer writer;

    private BasicFileWriter(@NonNull Writer writer) {
        this.writer = writer;
    }

    public static BasicFileWriter create(@NonNull LocalDateTime time){
        String fileName = FILE_NAME_FORMAT.format(time);
        File file = new File(fileName);

        BufferedWriter out = IoUtil.createFileAppender(file);

        return new BasicFileWriter(new Writer(out));
    }

    public void write(@NonNull FileMessage fileMessage) {
        String messageLine = createMessageLine(fileMessage);
        System.out.println(messageLine);

        writer.write(messageLine);
    }

    private static String createMessageLine(FileMessage fileMessage){
        String timeFormat = LocalDateTime.now().format(MESSAGE_TIME_FORMAT);

        return MessageFormat.format("{0} {1}\n", timeFormat, fileMessage.create());
    }
}
