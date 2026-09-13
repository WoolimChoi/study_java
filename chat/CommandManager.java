package chat;

import chat.command.Command;
import chat.command.NotFoundCommand;
import chat.command.SendMessageCommand;

import java.util.HashMap;
import java.util.Map;

import static chat.MyLogger.log;

public class CommandManager {

    private final Map<String, Command> CommandMap = new HashMap<>();
    private final Command defaultCommand = new SendMessageCommand();
    private final Command notFoundCommand = new NotFoundCommand();


    public void add(String command, Command value) {
        CommandMap.put(command, value);
    }

    public void findAndRunCommand(String received, Session session, SessionManager sessionManager) {
        try {
            if (received.startsWith("/")) {
                if (received.contains("@")) {
                    String[] split = received.split("@", 2);
                    String commandType = split[0];
                    String value = split[1];
                    if (value.isBlank()) throw new RuntimeException();

                    Command command = CommandMap.getOrDefault(commandType, notFoundCommand);
                    command.runCommand(value, session, sessionManager);

                } else {
                    Command command = CommandMap.getOrDefault(received, notFoundCommand);
                    command.runCommand(received, session, sessionManager);
                }
            } else {
                defaultCommand.runCommand(received, session, sessionManager);
            }
        } catch (Exception e) {
            session.sendMessage("값이 비어있습니다, 다시 입력해주세요.");
        }
    }
}
