package chat;

import java.io.DataInputStream;
import java.io.IOException;

import static chat.MyLogger.log;

public class ReadHandler implements Runnable{

    private final DataInputStream input;

    public ReadHandler(DataInputStream input) {
        this.input = input;
    }

    @Override
    public void run() {
        try (input) {
            while (true) {
                String received = input.readUTF();
                log(received);
                if (received.contains("연결 종료")) break;

            }
        } catch (IOException e) {
            log(e);
        }
    }
}
