package server;

import server.v4.MessageWriter;

public class App {
    public static void main(String args[]) {
        new Server(new MessageWriter(), 7777).start();
    }
}
