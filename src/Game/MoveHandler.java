package Game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MoveHandler extends MouseAdapter {

    Board board;
    int fromRow;
    int fromCol;
    int toRow;
    int toCol;
    Piece selectedPiece;
    Piece capturedPiece;

    public  MoveHandler(Board board) {
        this.board = board;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        fromRow = e.getY() / this.board.TILE_SIZE;
        fromCol = e.getX() / this.board.TILE_SIZE;
        this.selectedPiece = board.getPiece(fromRow, fromCol);
        this.board.calculateMoves(this.selectedPiece, true, fromRow, fromCol);
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
            Move mm = board.isValidMove(toRow, toCol);

            if (mm != null) {
                board.calculateConnectedMoves(mm);

                while (!board.connectedMoves.isEmpty()) {
                    Move m = board.connectedMoves.pop();
                    board.makeMove(m);
                }

                board.clearPossibleMoves();

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
