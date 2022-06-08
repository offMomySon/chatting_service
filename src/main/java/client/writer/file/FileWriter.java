package client.writer.file;

import client.message.file.FileMessage;
import common.Writer;
import java.io.BufferedWriter;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;

public class FileWriter implements FileWriterInterface{
    private static final DateTimeFormatter MESSAGE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private final Writer writer;

    private FileWriter(@NonNull Writer writer) {
        this.writer = writer;
    }

    public static FileWriter create(TimeNameFile timeNameFile){
        BufferedWriter out = timeNameFile.getBufferedWriter();

        return new FileWriter(new Writer(out));
    }

    public void write(@NonNull FileMessage fileMessage) {
        String messageLine = createMessageLine(fileMessage);

        writer.write(messageLine);
    }

    private static String createMessageLine(FileMessage fileMessage){
        String timeFormat = LocalDateTime.now().format(MESSAGE_TIME_FORMAT);

        return MessageFormat.format("{0} {1}\n", timeFormat, fileMessage.create());
    }
}
