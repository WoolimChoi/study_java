package chat;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {

    private List<Session> sessions = new ArrayList<>();

    public synchronized void add(Session session) {
        sessions.add(session);
    }

    public synchronized void remove(Session session) {
        sessions.remove(session);
    }

    public synchronized void closeAll() {
        for (Session session : sessions) {
            session.close();
        }
        sessions.clear();
    }

    public synchronized void sendMessageToAll(String message) {
        for (Session session : sessions) {
            session.sendMessage(message);
        }
    }

    public synchronized void sendJoinMessageToAll(String name) {
        for (Session session : sessions) {
            if (session.getName() != null) {
                session.sendMessage(name + "님이 입장했습니다.");
            }
        }
    }
}
