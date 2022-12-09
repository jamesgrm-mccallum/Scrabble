class Tile {
    Coordinate location;
    String type;
    Piece piece;
    
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
        return " ";
    }
    
}
