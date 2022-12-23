import java.util.ArrayList;
import java.util.Random;

class player {
    int playerID;
    String name;
    int points;
    ArrayList<Piece> deck = new ArrayList<Piece>();
    int passNum;

    public player(int playerID, String name, int points, ArrayList<Piece> deck) {
        this.playerID = playerID;
        this.name = name;
        this.points = points;
        this.deck = deck;
        this.passNum = 0;
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

    public int getPassNum() {
        return passNum;
    }

    public void setPassNum(int passNum) {
        this.passNum = passNum;
    }

    public String deckToString(){
        String deckString = "";
        for (Piece piece : deck){
            deckString += piece.getLetter().toUpperCase();
            deckString += " ";
        }
        
        return "[" + deckString.substring(0, deckString.length() - 1) + "]";
    }

    public void drawDeck(Board gameboard, Bag bag){
        Random rand = new Random();
        int difference = 7 - deck.size();
        for (int i = 0; i < difference; i++){
            int piece = rand.nextInt(0, bag.getContents().size());
            deck.add(bag.getContents().get(piece));
            bag.getContents().remove(piece);
        }
    }

    
}
