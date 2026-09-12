package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import static chat.CloseUtil.closeAll;
import static chat.MyLogger.log;

public class Session implements Runnable{

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final SessionManager sessionManager;
    private boolean closed = false;
    private String name;

    public Session(Socket socket, SessionManager sessionManager) throws IOException {
        this.socket = socket;
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        this.sessionManager = sessionManager;
        sessionManager.add(this);
    }

    @Override
    public void run() {
        try {
            name = input.readUTF();
            log(name + "님 채팅 입장");
            sessionManager.sendJoinMessageToAll(name);

            while (true) {
                String received = input.readUTF();
                if (received.equals("/exit")) {
                    sessionManager.sendExitMessageToAll(name);
                    break;
                }

            }
        } catch (IOException e) {
            log(e);
        } finally {
            sessionManager.remove(this);
            close();
        }
    }

    public synchronized void close() {
        if (closed) return;

        closeAll(socket, input, output);
        closed = true;
        log(name + "님 연결 종료 " + socket);
    }

    public void sendMessage(String message) {
        try {
            output.writeUTF(message);
        } catch (IOException e) {
            log(e);
        }
    }

    public String getName() {
        return name;
    }
}
