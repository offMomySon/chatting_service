package server;


import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import server.v3.AddressRepository;
import server.writer.smf.SmfSender;

@Slf4j
public class Server {
    private static final int MIN_PORT_NUM = 7777;

    private final SmfSender smfSender;
    private final AddressRepository addressRepository;
    private final int port;

    public Server(@NonNull SmfSender smfSender, @NonNull AddressRepository addressRepository, int port) {
        this.smfSender = smfSender;
        this.addressRepository = addressRepository;
        this.port = validate(port);
    }

    private static int validate(int port){
        if(port < MIN_PORT_NUM){
            throw new RuntimeException("Abnormal port number.");
        }
        return port;
    }

    public void start() {
        Socket socket;
        Thread sender = new Thread(
            () -> Sender.create(smfSender, System.in, addressRepository).waitAndThenSendMsg()
        );
        sender.start();

        try( ServerSocket serverSocket = new ServerSocket(port) ) {
            log.info("Server start.");

            while(true) {
                socket = serverSocket.accept();
                log.info("[{} : {}] is connected.", socket.getInetAddress(), socket.getPort());

                Address address = new Address(socket.getInetAddress().getHostAddress());
                OutputStream socketStream = socket.getOutputStream();

                addressRepository.addAddress(address);
                smfSender.addAddress(address, socketStream);
//                fileWriterCreator.addAddress(address);

                Socket _socket = socket;
                Thread receiver = new Thread(()-> Receiver.create(_socket, smfSender).waitAndThenGetMsg());
                receiver.start();
            }
        } catch(IOException e) {
            throw new RuntimeException("Fail connection.", e);
        }
    }
}