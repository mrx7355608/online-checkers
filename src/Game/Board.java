package Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class Board extends JPanel {

    public final int TILE_SIZE = 60;
    MovesCalculator movesCalculator = new MovesCalculator(this);
    ArrayList<Piece> pieces = new ArrayList<>();


    public Board() {
        super.setSize(500, 520);

        InputHandler mv = new InputHandler(this);
        this.addMouseListener(mv);
        this.addMouseMotionListener(mv);

        this.addPieces();
    }

    private void addPieces() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (i <= 2 && (i + j) % 2 == 0)
                    pieces.add(new Piece("black", i, j));
                else if (i >= 5 && (i + j) % 2 == 0)
                    pieces.add(new Piece("red", i, j));

            }
    }

    public Piece getPiece(int row, int col) {
        for (Piece p : this.pieces) {
            if (p.row == row && p.col == col) {
                return p;
            }
        }

        return null;
    }

    public void makeMove(Move move) {
        move.selectedPiece.row = move.toRow;
        move.selectedPiece.col = move.toCol;
        move.selectedPiece.x = move.toCol * TILE_SIZE;
        move.selectedPiece.y = move.toRow * TILE_SIZE;

        if (move.capturedPiece != null) {
            pieces.remove(move.capturedPiece);
        }

        this.repaint();
    }

    public Move isValidMove(int toRow, int toCol) {
        for (Move m : movesCalculator.possibleMoves) {
            if (m.toRow == toRow && m.toCol == toCol) {
                return m;
            }
        }

        return null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                g.setColor((i + j) % 2 == 0 ? new Color(208, 138, 69) : new Color(254, 206, 158));
                g.fillRect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        // Highlight possible moves (if any)
        for (Move m : movesCalculator.possibleMoves) {
            g.setColor(Color.pink);
            g.fillRect(m.toCol * TILE_SIZE, m.toRow * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }


        // Draw pieces
        for (Piece piece : this.pieces) {
            piece.draw(g);
        }

    }

}
