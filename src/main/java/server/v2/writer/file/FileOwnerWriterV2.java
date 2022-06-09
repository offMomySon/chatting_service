package server.v2.writer.file;

import common.MessageOwner;
import java.text.MessageFormat;
import lombok.NonNull;

public class FileOwnerWriterV2 implements FileWriterV2 {
    private final MessageOwner owner;
    private final FileWriterV2 fileWriterV2;

    public FileOwnerWriterV2(@NonNull MessageOwner owner, @NonNull FileWriterV2 fileWriterV2) {
        this.owner = owner;
        this.fileWriterV2 = fileWriterV2;
    }

    @Override
    public void write(String message) {
        String ownerMessage = MessageFormat.format("{0} {1}", owner, message);

        fileWriterV2.write(ownerMessage);
    }
}
