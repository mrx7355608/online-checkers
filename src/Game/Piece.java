package Game;

public class Piece {

    public final String team;
    public int row;
    public int col;
    public int x;
    public int y;

    public Piece(String team, int row, int col) {
        this.team = team;
        this.row = row;
        this.col = col;
        this.x = col * 60;
        this.y = row * 60;
    }
}
