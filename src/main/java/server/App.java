package server;

import server.writer.MessageWriter;

public class App {
    public static void main(String args[]) {
        new Server(new MessageWriter(), 7777).start();
    }
}
