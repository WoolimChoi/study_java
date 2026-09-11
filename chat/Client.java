package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


import static chat.MyLogger.log;

public class Client {

    public static final int PORT = 12345;

    static void main(String[] args) {
        log("클라이언트 시작");

        try {
            Socket socket = new Socket("localhost", PORT);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream((socket.getOutputStream()));

            WriteHandler writeHandler = new WriteHandler(output, socket);
            Thread writeThread = new Thread(writeHandler, "writeThread");

            ReadHandler readHandler = new ReadHandler(input, socket);
            Thread readThread = new Thread(readHandler, "readThread");

            writeThread.start();
            readThread.start();

        } catch (IOException e) {
            log(e);
        }
    }
}
