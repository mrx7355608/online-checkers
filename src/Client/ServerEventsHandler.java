package Client;

import Game.Board;
import Game.Checkers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerEventsHandler implements Runnable {

    private final Socket socket;
    private final Checkers game;

    public ServerEventsHandler(Socket s, Checkers checkers) {
        this.socket = s;
        this.game = checkers;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            while (true) {
                String input = in.readLine();
                String event = input.split(":")[0];
                String message = input.split(":")[1];

                switch (event) {
                    case "START_GAME" -> {
                        this.game.remove(this.game.initialLabel);
                        this.game.add(new Board());
                        this.game.repaint();
                    }
                }

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
