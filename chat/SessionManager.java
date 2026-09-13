package chat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

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

    public synchronized void broadcast(String name, Supplier<String> supplier) {
        for (Session session : sessions) {
            if (session.getName() != null) {
                session.sendMessage(name + supplier.get());
            }
        }
    }

    public List<Session> getSessions() {
        return sessions;
    }
}
