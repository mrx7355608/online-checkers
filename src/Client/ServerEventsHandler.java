package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerEventsHandler implements Runnable {

    private final Socket socket;

    public ServerEventsHandler(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            while (true) {
                System.out.println(in.readLine());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
