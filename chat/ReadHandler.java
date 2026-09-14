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
                System.out.println(received);

            }
        } catch (IOException e) {
            System.out.println("서버가 비정상 종료되었습니다. /exit을 입력해 방을 나가주세요");
        }
    }
}
