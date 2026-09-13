package chat.command;

import chat.Session;
import chat.SessionManager;

public class ChangeNameCommand implements Command{

    @Override
    public void runCommand(String changedName, Session session, SessionManager sessionManager) {
        sessionManager.broadcast(session.getName(), () -> " 님의 이름이 " + changedName + " 님으로 변경되었습니다");
        session.setName(changedName);
    }

}
