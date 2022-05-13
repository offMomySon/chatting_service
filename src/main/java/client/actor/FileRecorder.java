package client.actor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.NonNull;
import static util.IoUtil.createFileAppender;

public class FileRecorder {
    private final String writer;

    public FileRecorder(@NonNull String writer) {
        this.writer = writer;
    }

    public void accept(@NonNull String message) {
        Date now = new Date();
        String yyyyMMddHHmm = new SimpleDateFormat("yyyyMMddHHmm").format(now);

        File file = new File(yyyyMMddHHmm);
        createFileIfNotExist(file);
        writeWithFile(file,message,now);
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
}
