package server;

import server.v5.AddressWriter;

public class App {
    public static void main(String args[]) {
        new Server(new AddressWriter(), 7777).start();
    }
}
