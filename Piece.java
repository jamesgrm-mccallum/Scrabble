/**
 * A Piece is an object that represents a letter on a tile
 */
class Piece {
    private String letter;
    
    /**
     * Constructor for a piece object
     * @param letter letter for the piece to represent
     */
    public Piece(String letter){
        this.letter = letter.toUpperCase();
    }

    /**
     * This function returns the letter of the tile
     * 
     * @return The letter variable is being returned.
     */
    public String getLetter() {
        return letter;
    }

    /**
     * This function sets the letter of the tile to the letter passed in
     * 
     * @param letter The letter that the user has guessed.
     */
    public void setLetter(String letter) {
        this.letter = letter;
    }

    @Override
    public String toString(){
        return letter;
    }

}
