package chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import static chat.MyLogger.log;

public class ReadHandler implements Runnable{

    private final DataInputStream input;
    private final Socket socket;

    public ReadHandler(DataInputStream input, Socket socket) {
        this.input = input;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (socket;
             input) {

            while (true) {
                String received = input.readUTF();
                log(received);
                if (received.equals("/exit")) break;

            }
        } catch (IOException e) {
            log(e);
        }
    }
}
