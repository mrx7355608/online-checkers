package Game;

public class Move {
    int fromRow;
    int fromCol;
    int toRow;
    int toCol;
    Piece selectedPiece;
    Piece capturedPiece;

    public Move(int fr, int fc, int tr, int tc, Piece selectedPiece) {
        this.fromRow = fr;
        this.fromCol = fc;
        this.toRow = tr;
        this.toCol = tc;
        this.selectedPiece = selectedPiece;
        this.capturedPiece = null;
    }

    public Move(int fr, int fc, int tr, int tc, Piece selectedPiece, Piece capturedPiece) {
        this.fromRow = fr;
        this.fromCol = fc;
        this.toRow = tr;
        this.toCol = tc;
        this.selectedPiece = selectedPiece;
        this.capturedPiece = capturedPiece;
    }

}
