package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static chat.MyLogger.log;

public class CloseUtil {

    public static void closeAll(Socket socket, DataInputStream input, DataOutputStream output) {
        close(input);
        close(output);
        close(socket);
    }

    public static void close(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                log(e);
            }
        }
    }

    public static void close(DataInputStream input) {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                log(e);
            }
        }
    }

    public static void close(DataOutputStream output) {
        if (output != null) {
            try {
                output.close();
            } catch (IOException e) {
                log(e);
            }
        }
    }


}
