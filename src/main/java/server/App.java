package server;

import server.writer.file.FileWriter;
import server.writer.smf.SmfSender;

public class App {
    public static void main(String args[]) {
        new Server(new SmfSender(), new FileWriter(), 7777).start();
    }
}
