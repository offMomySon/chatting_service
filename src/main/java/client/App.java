package client;

public class App {
    public static void main(String args[]) {
        Client client = new Client( "127.0.0.1", 7777);
        client.start();
    }
}
