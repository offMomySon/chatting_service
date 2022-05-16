package server;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Server {
    private final int port;

    public Server(int port) {
        this.port = validate(port);
    }

    private static int validate(int port){
        if(port <= 0){
            throw new RuntimeException("Abnormal port number.");
        }
        return port;
    }

    public void start() {
//        Socket socket;
//        Thread sender = new Thread(
//            () -> Sender.from(ipRepository)
//                .waitAndThenSendMsg()
//        );
//        sender.start();
//
//        try( ServerSocket serverSocket = new ServerSocket(port) ) {
//            log.info("Server start.");
//
//            while(true) {
//                socket = serverSocket.accept();
//                log.info("[{} : {}] is connected.", socket.getInetAddress(), socket.getPort());
//
//                ipRepository.put(IpAddress.create(socket.getInetAddress().getHostAddress()), createWriter(socket.getOutputStream()));
//                log.info("Current user count : {}", ipRepository.getSize());
//
//                Socket _socket = socket;
//                Thread receiver = new Thread(
//                    ()-> new Receiver(_socket,ipRepository)
//                        .waitAndThenGetMsg()
//                );
//                receiver.start();
//            }
//        } catch(IOException e) {
//            throw new RuntimeException("Fail connection.", e);
//        }
    }
}