package chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import static chat.MyLogger.log;

public class WriteHandler implements Runnable{

    private final DataOutputStream output;
    private final Socket socket;

    public WriteHandler(DataOutputStream output, Socket socket) {
        this.output = output;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("/join (name) 입력\n");
            String sendName = scanner.nextLine();
            output.writeUTF(sendName);

            while (true) {
                Thread.sleep(1000); //입장 완료 문자 대기
                System.out.println("/message || /change (name) || /users || /exit");

                String toSend = scanner.nextLine();
                output.writeUTF(toSend);

                if (toSend.equals("/exit")) break;
            }
        } catch (IOException | InterruptedException e) {
            log(e);
        } finally {
            try {
                Thread.sleep(1000); //input.close() 완료할 때까지 대기
                output.close();
                socket.close();
            } catch (InterruptedException | IOException e) {
                log(e);
            }

        }
    }
}
