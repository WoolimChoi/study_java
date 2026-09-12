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
        String joinMessage, command, name;
        do {
            try {
                System.out.print("Enter /join@name: ");
                joinMessage = scanner.nextLine();
                String[] split = joinMessage.split("@", 2);
                command = split[0];
                name = split[1].trim();

                if (!command.equals("/join")) {
                    System.out.println("잘못된 명령어입니다, 다시 입력하세요");
                } else if (name.isBlank()) {
                    System.out.println("이름은 공백일 수 없습니다, 다시 입력하세요");
                } else break;
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다, 다시 입력하세요");
            }
        } while (true);

        return name;
    }
}

