public class GameHandler implements Runnable {
    private final Player player1;
    private final Player player2;

    public GameHandler(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {
        while (true) {
            // MoveRequest message from player1
        }
    }
}
