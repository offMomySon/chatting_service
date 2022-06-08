package server.v2.writer.file;

import common.Writer;
import java.io.BufferedWriter;
import java.io.File;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import server.Address;
import util.IoUtil;

public class TimeAndIpNamedFileWriterV2 implements FileWriterV2 {
    private static final DateTimeFormatter FILE_NAME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    private final Writer writer;

    private TimeAndIpNamedFileWriterV2(@NonNull Writer writer) {
        this.writer = writer;
    }

    public static TimeAndIpNamedFileWriterV2 create(LocalDateTime localDateTime, Address address){
        String fileName = MessageFormat.format("{0}_{1}", FILE_NAME_FORMAT.format(localDateTime), address.getValue());
        File file = new File(fileName);

        BufferedWriter out = IoUtil.createFileAppender(file);

        return new TimeAndIpNamedFileWriterV2(new Writer(out));
    }

    @Override
    public void write(String message){
        writer.write(message);
    }
}
