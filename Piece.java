class Piece {
    String letter;
    int pointVal;

    public Piece(String letter, int pointVal){
        this.letter = letter;
        this.pointVal = pointVal;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getPointVal() {
        return pointVal;
    }

    public void setPointVal(int pointVal) {
        this.pointVal = pointVal;
    }
}
