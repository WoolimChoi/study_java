package chat.command;

import chat.Session;
import chat.SessionManager;

import java.io.DataOutputStream;
import java.io.IOException;

import static chat.MyLogger.log;

public class SendMessageCommand implements Command{
    @Override
    public void runCommand(String received, Session session, SessionManager sessionManager) {
        String senderName = session.getName();
        for (Session newSession : sessionManager.getSessions()) {
            if (newSession.getName() != null) {
                newSession.sendMessage("[" + senderName + "]: " + received);
            }
        }
    }
}
