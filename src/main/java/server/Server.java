package server;


import common.repository.AddressRepository;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;
import server.destination.address.Address;

@Slf4j
public class Server {
    private static final int MIN_PORT = 7777;

    private final AddressRepository addressRepository = new AddressRepository();
    private final int port;

    public Server(int port) {
        this.port = validate(port);
    }

    private static int validate(int port){
        if(port < MIN_PORT){
            throw new RuntimeException("Abnormal port number.");
        }
        return port;
    }

    public void start() {
        Socket socket;
        Thread sender = new Thread(
            () -> new Sender(addressRepository).waitAndThenSendMsg()
        );
        sender.start();

        try( ServerSocket serverSocket = new ServerSocket(port) ) {
            log.info("Server start.");

            while(true) {
                socket = serverSocket.accept();
                log.info("[{} : {}] is connected.", socket.getInetAddress(), socket.getPort());

                addressRepository.put(new Address(socket.getInetAddress().getHostAddress()), socket.getOutputStream());

                Socket _socket = socket;
                Thread receiver = new Thread(()-> new Receiver(_socket).waitAndThenGetMsg());
                receiver.start();
            }
        } catch(IOException e) {
            throw new RuntimeException("Fail connection.", e);
        }
    }
}