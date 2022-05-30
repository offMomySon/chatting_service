package client.format;

import common.view.console.ConsoleMessage;
import common.view.file.FileMessage;
import java.io.Serializable;

public interface SimpleMessageFormat extends Serializable {
    public ConsoleMessage createConsoleForm();
    public FileMessage createFileForm();
}
