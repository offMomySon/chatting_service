package server;

import server.sender.v2.FileWriter;
import server.sender.v2.SmfSender;

public class App {
    public static void main(String args[]) {
        new Server(new SmfSender(), new FileWriter(), 7777).start();
    }
}
