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
            String received = input.readUTF();
            name = received.substring(6);
            log(name + "님 입장");
            output.writeUTF(name + "님 입장 완료");

            while (true) {
                received = input.readUTF();

                if (received.contains("/message")) {
                    String message = received.substring(9);
                    sendMessageToAll(message, name);

                } else if (received.contains("/change")) {
                    name = received.substring(8);
                    output.writeUTF("사용자명 변경 완료: " + name + "로 변경");

                } else if (received.equals("/users")) {
                    sendUserList();

                } else {
                    log(name + "님 측에서 연결 종료 요청");
                    output.writeUTF(name + "님 연결 종료");
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
        try {
            output.writeUTF(name + "님 서버가 비정상적으로 종류되었습니다. /exit를 입력하여 종료해주시기 바랍니다.");
        } catch (IOException e) {
            log(e);
        }

        closeAll(socket, input, output);
        closed = true;
        log(name + "님 연결 종료 " + socket);
    }

    private void sendMessageToAll(String message, String name) {
        for (Session session : sessionManager.getSessions()) {
            try {
                session.output.writeUTF(name + ": " + message);
            } catch (IOException e) {
                log(e);
            }
        }
    }


    private void sendUserList() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("사용자 목록: \n");
        for (Session session : sessionManager.getSessions()) {
            sb.append(session.name + '\n');
        }
        String userList = sb.toString();
        output.writeUTF(userList);
    }

}
