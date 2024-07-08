package Game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import Client.*;

public class Checkers extends JFrame {

    public JLabel initialLabel;

    public Checkers() {
        super.setSize(495, 519);
        super.setTitle("Checkers");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setResizable(false);
        super.setLocationRelativeTo(null);

        initialLabel = new JLabel("Waiting for another player to join...", JLabel.CENTER);
        initialLabel.setFont(new Font("Fira Code", Font.BOLD, 20));
        super.add(initialLabel);
    }

    public static void main(String[] args) {
        // Take ip as input
        String ip = JOptionPane.showInputDialog("Enter server ip:");
        JOptionPane.showMessageDialog(null, "Connecting to server...");

        try {
            // Connect to server
            Client client = Client.getInstance();
            Socket socket = client.connect(ip);

            // Start game gui on main thread
            Checkers checkers = new Checkers();
            checkers.setVisible(true);

            // Spawn a new thread to receive server messages in parallel with game gui
            Thread thread = new Thread(new ServerEventsHandler(socket, checkers));
            thread.start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to connect to server", "Connection Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
