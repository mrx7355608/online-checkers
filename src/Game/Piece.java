package Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Piece {

    private final Image image;
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

        try {
            if (team.equals("red")) {
                image = ImageIO.read(new File("src/assets/red_checker.png")).getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            } else {
                image = ImageIO.read(new File("src/assets/black_checker.png")).getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics g) {
        // Here 60 is the tile size, so image's 0x0 will be on tile's 0x0 and the
        // image will fit inside the tile rectangle
        g.drawImage(this.image, this.x, this.y, null);
    }

}
