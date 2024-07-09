import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {
    String name;
    String team;
    int remainingPieces;
    Socket socket;
    BufferedReader in;
    PrintWriter out;

    public Player(String name, String team, Socket socket) {
        this.name = name;
        this.team = team;
        this.remainingPieces = 12;
        this.socket = socket;
        this.createInputOutputStreams();
    }

    private void createInputOutputStreams() {
        try {
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
