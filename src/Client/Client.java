package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private static Client instance;

    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }

        return instance;
    }

    private Client() {
        this.socket = null;
    }

    public Socket connect(String ip) throws IOException {
        socket = new Socket(ip, 5555);
        return socket;

    }

    public void sendMove(String move) throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream());
        out.println(move);
        out.flush();
        out.close();
    }

}
