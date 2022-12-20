import java.util.ArrayList;

class player {
    int playerID;
    String name;
    int points;
    ArrayList<Piece> deck = new ArrayList<Piece>();

    public player(int playerID, String name, int points, ArrayList<Piece> deck) {
        this.playerID = playerID;
        this.name = name;
        this.points = points;
        this.deck = deck;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public ArrayList<Piece> getDeck() {
        return deck;
    }
    public void setDeck(ArrayList<Piece> deck) {
        this.deck = deck;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String deckToString(){
        String deckString = "";
        for (Piece piece : deck){
            deckString += piece.getLetter().toUpperCase();
            deckString += " ";
        }
        
        return "[" + deckString.substring(0, deckString.length() - 1) + "]";
    }
}
