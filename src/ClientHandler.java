import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String data;

            while (this.socket.isConnected()) {
                if ((data = in.readLine()) != null) {
                    System.out.println(data);
                }
            }

        } catch (IOException e) {
            System.out.println("An error occurred, while handling a client");
        }
    }
}
