package Game;

import java.util.ArrayList;

public class Board {

    public final int TILE_SIZE = 60;
    MovesCalculator movesCalculator = new MovesCalculator(this);
    ArrayList<Piece> pieces = new ArrayList<>();


    public Board() {
        this.addPieces();
    }

    private void addPieces() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i <= 2 && (i + j) % 2 == 0)
                    pieces.add(new Piece("black", i, j));
                else if (i >= 5 && (i + j) % 2 == 0)
                    pieces.add(new Piece("red", i, j));

            }
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
    }

    public Move isValidMove(int toRow, int toCol) {
        for (Move m : movesCalculator.possibleMoves) {
            if (m.toRow == toRow && m.toCol == toCol) {
                return m;
            }
        }

        return null;
    }
}
