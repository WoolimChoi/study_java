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
    private final CommandManager commandManager;
    private boolean closed = false;
    private String name;

    public Session(Socket socket, SessionManager sessionManager, CommandManager commandManager) throws IOException {
        this.socket = socket;
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        this.sessionManager = sessionManager;
        sessionManager.add(this);
        this.commandManager = commandManager;
    }

    @Override
    public void run() {
        try {
            name = input.readUTF();
            log(name + "님 채팅 입장");
            sessionManager.broadcast(name, () -> "님이 입장했습니다.");

            while (true) {
                String received = input.readUTF();
                if (received.equals("/exit")) {
                    sessionManager.broadcast(name, () -> "님이 방을 나갔습니다.");
                    break;
                }
                commandManager.findAndRunCommand(received, this, sessionManager);
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

    public void setName(String name) {
        this.name = name;
    }
}
