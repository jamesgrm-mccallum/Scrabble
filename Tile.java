/**
 * The Tile class is a class that represents a tile on the board. It has a location, a type, and a
 * piece. The type is the type of tile it is (2L, 2W, 3L, 3W, STAR, or null). The piece is the piece
 * that is on the tile (if there is one). The location is the location of the tile on the board
 */
class Tile {
    private Coordinate location;
    private String type;
    private Piece piece;
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";

    /**
     * Constructor for a tile object
     * @param location location of the tile on a board
     * @param type type of tile
     * @param piece piece on the tile
     * 
     */
    public Tile(Coordinate location, String type, Piece piece) {
        this.location = location;
        this.type = type;
        this.piece = piece;
    }

    /**
     * This function returns the location of the object
     * 
     * @return The location of the object.
     */
    public Coordinate getLocation() {
        return location;
    }

    /**
     * This function sets the location of the object to the location passed in as a parameter
     * 
     * @param location The location of the marker.
     */
    public void setLocation(Coordinate location) {
        this.location = location;
    }

    /**
     * This function returns the type of the object
     * 
     * @return The type of the object.
     */
    public String getType() {
        return type;
    }

    /**
     * This function sets the type of the object
     * 
     * @param type The type of the event.
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * This function returns the piece that is on the square
     * 
     * @return The piece object.
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * This function sets the piece of the square to the piece passed in
     * 
     * @param piece The piece that is on the square.
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * If the tile has a letter, display the letter. If the tile has a special type, display the
     // Overriding the toString method.
     * special type. If the tile has neither, display nothing.
     * 
     * @return The method returns a string.
     */
    public String toString(){
        if (piece != null){ //displays letter if it has one (with special colors)
            return ANSI_YELLOW  + piece.getLetter() + ANSI_RESET;
        }
        //displays special tile if it has one and doesn't have a letter
        else if (type != null){
            if (type.equals("2L")){
                return "1";
            }
            else if (type.equals("2W")){
                return "2";
            }
            else if (type.equals("3L")){
                return "3";
            }
            else if (type.equals("3W")){
                return "4";
            }
            else if (type.equals("STAR")){
                return "*";
            }

            else {
                return " ";
            }
        }
                    //displays nothing if all other previous cases do not apply
        else {
            return " ";
        }
    }   
    
}
