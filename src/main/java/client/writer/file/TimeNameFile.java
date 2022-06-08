package client.writer.file;

import java.io.BufferedWriter;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import util.IoUtil;

public class TimeNameFile {
    private static final DateTimeFormatter FILE_NAME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    private final File value;

    private TimeNameFile(@NonNull File value) {
        this.value = value;
    }

    public static TimeNameFile create(@NonNull LocalDateTime time){
        String format = FILE_NAME_FORMAT.format(time);

        return new TimeNameFile(new File(format));
    }

    public BufferedWriter getBufferedWriter(){
        return IoUtil.createFileAppender(value);
    }
}
