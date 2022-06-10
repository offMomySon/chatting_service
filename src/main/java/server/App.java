package server;

import server.v2.writer.file.BasicFileWriterCreatorV2;
import server.writer.smf.SmfSender;

public class App {
    public static void main(String args[]) {
        new Server(new SmfSender(), new BasicFileWriterCreatorV2(), 7777).start();
    }
}
