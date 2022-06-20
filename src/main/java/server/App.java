package server;

import server.v5.MessageWriter;

public class App {
    public static void main(String args[]) {
        new Server(new MessageWriter(), 7777).start();
    }
}
