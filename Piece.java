class Piece {
    char letter;
    int pointVal;

    public Piece(char letter, int pointVal){
        this.letter = letter;
        this.pointVal = pointVal;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int getPointVal() {
        return pointVal;
    }

    public void setPointVal(int pointVal) {
        this.pointVal = pointVal;
    }
}
