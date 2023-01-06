class Piece {
    String letter;
    


    public Piece(String letter){
        this.letter = letter.toUpperCase();
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    @Override
    public String toString(){
        return letter;
    }

}
