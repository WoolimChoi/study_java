package chat.command;

import chat.Session;
import chat.SessionManager;

public class NotFoundCommand implements Command{
    @Override
    public void runCommand(String received, Session session, SessionManager sessionManager) {
        session.sendMessage("올바르지 않은 명령어입니다, 다시 입력해주세요.");
    }
}
