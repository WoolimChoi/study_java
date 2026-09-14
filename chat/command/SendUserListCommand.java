package chat.command;

import chat.Session;
import chat.SessionManager;

public class SendUserListCommand implements Command{
    @Override
    public void runCommand(String received, Session session, SessionManager sessionManager) {
        int countUser = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("[접속자 목록]\n");
        for (Session newSession : sessionManager.getSessions()) {
            if (newSession.getName() != null) {
                if (newSession.getName() == session.getName()) {
                    sb.append("- " + newSession.getName() + " (나)\n");
                    countUser++;
                    continue;
                }
                sb.append("- " + newSession.getName() + "\n");
                countUser++;
            }
        }
        sb.append("[총 " + countUser + "명]");
        session.sendMessage(sb.toString());
    }
}
