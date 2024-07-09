import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final Player[] onlineClients = new Player[2];

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            // Create server
            ServerSocket server = new ServerSocket(5555);
            System.out.println("Server is listening on port: 5555");

            // Accept first client connection
            Socket socket1 = server.accept();
            Player player1 = new Player("Player 1", "red", socket1);
            onlineClients[0] = player1;
            executor.submit(new ClientHandler(player1));

            // Accept second client connection
            Socket socket2 = server.accept();
            Player player2 = new Player("Player 2", "black", socket2);
            onlineClients[1] = player2;
            executor.submit(new ClientHandler(player2));

            // Broadcast a message to start game
            broadcastMessage("START_GAME: both clients connected");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void broadcastMessage(String message) {
        for (Player player: onlineClients) {
            player.out.println(message);
            player.out.flush();
        }
    }

}
