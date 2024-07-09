import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Player player;

    public ClientHandler(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String data;

            while (this.socket.isConnected()) {

            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
