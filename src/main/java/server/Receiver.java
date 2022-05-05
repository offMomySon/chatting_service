package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;
import lombok.NonNull;
import repository.IpOutputStreamRepository;
import static util.IoUtil.createReader;

public class Receiver implements Runnable{
    public static final char[] cMsg  = new char[20];

    private final IpOutputStreamRepository ipRepository;
    private final InetAddress inetAddress;
    private final int port;
    private final BufferedReader in;

    private Receiver(@NonNull BufferedReader in, @NonNull InetAddress inetAddress, int port, @NonNull IpOutputStreamRepository ipRepository) {
        this.inetAddress = inetAddress;
        this.port = port;
        this.in = in;
        this.ipRepository = ipRepository;
    }

    public static Receiver create(@NonNull Socket socket, @NonNull IpOutputStreamRepository ipRepository){
        BufferedReader reader = null;
        try {
            reader = createReader(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("BufferedReader 변환에 실패했습니다.", e);
        }

        return new Receiver(reader, socket.getInetAddress(), socket.getPort(), ipRepository);
    }

    public void run() {
        try {
            int readCount;

            while( (readCount = in.read(cMsg)) != -1 ){
                String msg = String.valueOf(cMsg,0, readCount);
                System.out.println("client 로 부터 받은 메세지 : " + msg);
            }

        } catch(IOException e) {
            throw new RuntimeException("msg 수신에 실패했습니다.", e);
        } finally {
            System.out.println("# ["+inetAddress+ ":" +port+"]"+" 님이 나가셨습니다.");
            ipRepository.remove(inetAddress);
            System.out.println("현재 서버접속자 수는 "+ ipRepository.getSize()+"입니다.");
        }
    }
}