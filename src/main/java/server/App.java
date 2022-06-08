package server;

import server.v2.writer.file.TimeAndIpNamedFileWriterCreatorV2;
import server.writer.smf.SmfSender;

public class App {
    public static void main(String args[]) {
        new Server(new SmfSender(), new TimeAndIpNamedFileWriterCreatorV2(), 7777).start();
    }
}
