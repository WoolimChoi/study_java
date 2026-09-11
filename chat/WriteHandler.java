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
        try(socket;
            output) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter /join (name): ");
            String name = enterName(scanner);
            output.writeUTF(name);

            while (true) {

                String toSend = scanner.nextLine();
                output.writeUTF(toSend);

                if (toSend.equals("/exit")) break;
            }
        } catch (IOException e) {
            log(e);
        }

     }

    private String enterName(Scanner scanner) {
        String name;
        do {
            name = scanner.nextLine();
            if (name.startsWith("/join ")) break;
            System.out.println("잘못된 입력입니다, 다시 입력하세요.");
        } while (true);

        return name;
    }
}

