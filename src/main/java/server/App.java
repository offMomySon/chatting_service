package server;

import server.v3.AddressRepository;
import server.writer.smf.SmfSender;

public class App {
    public static void main(String args[]) {
        new Server(new SmfSender(), new AddressRepository(), 7777).start();
    }
}
