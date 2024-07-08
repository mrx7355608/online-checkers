import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final Socket[] onlineClients = new Socket[2];

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            // Create server
            ServerSocket server = new ServerSocket(5555);
            System.out.println("Server is listening on port: 5555");

            while (true) {
                // Accept a client connection
                Socket socket = server.accept();
                System.out.println(socket.getInetAddress() + " connected");
                if (onlineClients[0] == null) {
                    onlineClients[0] = socket;
                } else if (onlineClients[1] == null) {
                    onlineClients[1] = socket;
                    broadcastMessage("START_GAME: both players have connected");
                } else {
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    out.println("ERROR: A match is already in progress");
                    out.flush();
                }

                // Spawn a new thread to receive messages from client
                executor.submit(new ClientHandler(socket));
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void broadcastMessage(String message) {
        for (Socket s : onlineClients) {
            if (s == null)
                continue;

            try {
                PrintWriter out = new PrintWriter(s.getOutputStream());
                out.println(message);
                out.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
