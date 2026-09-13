package chat.command;


import chat.Session;
import chat.SessionManager;



public interface Command {
    void runCommand(String received, Session session, SessionManager sessionManager);
}
