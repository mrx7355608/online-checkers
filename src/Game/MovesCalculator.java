package Game;

import java.util.ArrayList;
import java.util.Stack;

public class MovesCalculator {

    public Stack<Move> possibleMoves = new Stack<>();
    public Stack<Move> connectedMoves = new Stack<>();
    private final Board board;

    public MovesCalculator(Board board) {
        this.board = board;
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
            Piece pieceAtDiagonal = this.board.getPiece(c.row, c.col);

            if (pieceAtDiagonal == null) {
                if (checkEmpty) {
                    possibleMoves.push(new Move(piece.row, piece.col, c.row, c.col, piece));
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

                    if (this.board.getPiece(toRow, toCol) == null) {
                        if (toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7)
                            continue;
                        possibleMoves.push(new Move(initRow, initCol, toRow, toCol, piece, pieceAtDiagonal));
                        calculateMoves(piece, false, toRow, toCol);
                    }

                }
            }
        }

    }

    public void clearAll() {
        connectedMoves.clear();
        possibleMoves.clear();
    }

    public void calculateConnectedMoves(Move destination) {
        connectedMoves.push(destination);

        while (!possibleMoves.empty()) {
            Move source = possibleMoves.pop();
            if (source.toRow == destination.fromRow && source.toCol == destination.fromCol) {
                connectedMoves.push(source);
                destination = source;
            }
        }
    }
}
