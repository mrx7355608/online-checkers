import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final Player[] onlineClients = new Player[2];

    public static void main(String[] args) {
        try {
            // Create server
            ServerSocket server = new ServerSocket(5555);
            System.out.println("Server is listening on port: 5555");

            // Accept first client connection
            Socket socket1 = server.accept();
            Player player1 = new Player("Player 1", "red", socket1);
            onlineClients[0] = player1;

            // Accept second client connection
            Socket socket2 = server.accept();
            Player player2 = new Player("Player 2", "black", socket2);
            onlineClients[1] = player2;
            
            // Start a new thread for handling game when
            // both clients have connected
            new Thread(new GameHandler(player1, player2)).start();

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
