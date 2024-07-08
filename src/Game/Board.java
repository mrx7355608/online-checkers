package Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class Board extends JPanel {

    public final int TILE_SIZE = 60;

    ArrayList<Piece> pieces = new ArrayList<>();
    public ArrayList<Move> moves = new ArrayList<>();
    private final Stack<Move> possibleMoves = new Stack<>();
    public Stack<Move> connectedMoves = new Stack<>();

    public Board() {
        super.setSize(500, 520);

        MoveHandler mv = new MoveHandler(this);
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

    public ArrayList<Coordinates> calculateDiagonals(Piece piece, int initRow, int initCol) {
        ArrayList<Coordinates> diagonals = new ArrayList<>();

        // Get diagonals
        int col1 = initCol + 1;
        int col2 = initCol - 1;
        int row;

        if (piece.team.equals("red")) {
            row = initRow - 1;
        } else {
            row = initRow + 1;
        }
        diagonals.add(new Coordinates(row, col1));
        diagonals.add(new Coordinates(row, col2));

        return diagonals;
    }

    public void calculateMoves(Piece piece, boolean checkEmpty, int initRow, int initCol) {
        ArrayList<Coordinates> diagonals = this.calculateDiagonals(piece, initRow, initCol);

        for (Coordinates c : diagonals) {
            // If diagonal's row or col if out of the board i.e. row > 7 or col > 7,
            // then ignore it
            if (c.row > 7 || c.row < 0 || c.col > 7 || c.col < 0)
                continue;

            // If diagonal is empty, then add it into moves
            Piece pieceAtDiagonal = this.getPiece(c.row, c.col);

            if (pieceAtDiagonal == null) {
                if (checkEmpty) {
                    moves.add(new Move(piece.row, piece.col, c.row, c.col, piece));
                }
            }
            else {
                // If diagonal has a piece, and it's enemy team piece, then find the
                // diagonal next to current diagonal and if that's empty, then add it to moves,
                // otherwise ignore
                if (!pieceAtDiagonal.team.equals(piece.team)) {
                    int toRow = piece.team.equals("red") ? initRow - 2 : initRow + 2;
                    int toCol;

                    if (initCol - pieceAtDiagonal.col == -1)
                        toCol = initCol + 2;
                    else
                        toCol = initCol - 2;

                    if (this.getPiece(toRow, toCol) == null) {
                        if (toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7)
                            continue;
                        moves.add(new Move(initRow, initCol, toRow, toCol, piece, pieceAtDiagonal));
                        calculateMoves(piece, false, toRow, toCol);
                    }

                }
            }
        }

    }

    public void clearPossibleMoves() {
        connectedMoves.clear();
        moves.clear();
    }

    public void calculateConnectedMoves(Move destination) {
        connectedMoves.push(destination);

        for (Move source : moves) {
            if (source.toRow == destination.fromRow && source.toCol == destination.fromCol) {
                System.out.println("found connected move");
                connectedMoves.push(source);
            }
        }
    }

    public Move isValidMove(int toRow, int toCol) {
        for (Move m : moves) {
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
        for (Move m : moves) {
            g.setColor(Color.pink);
            g.fillRect(m.toCol * TILE_SIZE, m.toRow * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }


        // Draw pieces
        for (Piece piece : this.pieces) {
            piece.draw(g);
        }

    }

}
