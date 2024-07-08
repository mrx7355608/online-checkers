package Game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InputHandler extends MouseAdapter {

    Board board;
    MovesCalculator movesCalculator;

    int fromRow;
    int fromCol;
    int toRow;
    int toCol;
    Piece selectedPiece;
    Piece capturedPiece;

    public InputHandler(Board board) {
        this.board = board;
        this.movesCalculator = board.movesCalculator;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        fromRow = e.getY() / this.board.TILE_SIZE;
        fromCol = e.getX() / this.board.TILE_SIZE;
        this.selectedPiece = board.getPiece(fromRow, fromCol);
        this.movesCalculator.calculateMoves(this.selectedPiece, true, fromRow, fromCol);
        this.board.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX() - board.TILE_SIZE / 2;
        int y = e.getY() - board.TILE_SIZE / 2;

        if (this.selectedPiece != null) {
            this.selectedPiece.x = x;
            this.selectedPiece.y = y;
            board.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        toRow = e.getY() / this.board.TILE_SIZE;
        toCol = e.getX() / this.board.TILE_SIZE;

        if (this.selectedPiece != null) {
            // If the move is valid, find all the moves that are connected to it.
            // It is used to capture pieces where user jumps over multiple enemy pieces.
            Move move = board.isValidMove(toRow, toCol);

            if (move != null) {
                movesCalculator.calculateConnectedMoves(move);
                while (!movesCalculator.connectedMoves.isEmpty()) {
                    board.makeMove(movesCalculator.connectedMoves.pop());
                }
                movesCalculator.clearAll();
            } else {
                selectedPiece.row = fromRow;
                selectedPiece.col = fromCol;
                selectedPiece.x = fromCol * board.TILE_SIZE;
                selectedPiece.y = fromRow * board.TILE_SIZE;
                board.repaint();
            }
        }
    }

}
