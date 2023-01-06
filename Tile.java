class Tile {
    Coordinate location;
    String type;
    Piece piece;
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    
    public Tile(Coordinate location, String type, Piece piece) {
        this.location = location;
        this.type = type;
        this.piece = piece;
    }

    public Coordinate getLocation() {
        return location;
    }
    public void setLocation(Coordinate location) {
        this.location = location;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Piece getPiece() {
        return piece;
    }
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public String toString(){
        if (piece != null){
            return ANSI_YELLOW  + piece.getLetter() + ANSI_RESET;
        }
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
        else {
            return " ";
        }
    }   
    
}
