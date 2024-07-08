package Game;

import javax.swing.*;

public class Checkers extends JFrame {

    public Checkers() {
        super.setSize(495, 519);
        super.setTitle("Checkers");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setResizable(false);
        super.setLocationRelativeTo(null);
        Board board = new Board();
        super.add(board);
    }

    public static void main(String[] args) {
        // Take ip as input
//        String ip = JOptionPane.showInputDialog("Enter server ip:");
//        JOptionPane.showMessageDialog(null, "Connecting to server...");

//        try {
            // Connect to server
//            Client client = Client.getInstance();
//            Socket socket = client.connect(ip);

            // Spawn a new thread to receive server messages in parallel with game gui
//            Thread thread = new Thread() {
//                @Override
//                public void run() {
//                    new MoveHandler().receiveMessages(socket, checkersBoard);
//                }
//            };
//            thread.start();

            // Start game gui on main thread
            new Checkers().setVisible(true);

//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(null, "Unable to connect to server", "Connection Failed", JOptionPane.ERROR_MESSAGE);
//        }
    }
}
