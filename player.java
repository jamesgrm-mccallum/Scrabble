import java.util.ArrayList;
import java.util.Random;

/**
 * The player class is used to store the player's name, points, deck, and pass number
 */
class Player {
    private int playerID;
    private String name;
    private int points;
    private ArrayList<Piece> deck = new ArrayList<Piece>();
    private int passNum;

    /**
    * This is a constructor for the player class. It is used to create a new player object.
    * @param playerID number to identify players
    * @param name user assigned name for a player
    * @param points points that a player has
    * @param deck deck of pieces 
    */
    public Player(int playerID, String name, int points, ArrayList<Piece> deck) {
        this.playerID = playerID;
        this.name = name;
        this.points = points;
        this.deck = deck;
        this.passNum = 0;
    }
    
    
    /**
     * This function returns the name of the person
     * 
     * @return The name of the person.
     */
    public String getName() {
        return name;
    }

    /**
     * This function sets the name of the object to the name passed in as a parameter
     * 
     * @param name The name of the parameter.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This function returns the points of the player
     * 
     * @return The points variable is being returned.
     */
    public int getPoints() {
        return points;
    }

    /**
     * This function sets the points of the player to the value of the parameter points
     * 
     * @param points The number of points the player has.
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * This function returns the deck of pieces
     * 
     * @return The deck of pieces.
     */
    public ArrayList<Piece> getDeck() {
        return deck;
    }

    /**
     * This function sets the deck of the game to the deck passed in as a parameter
     * 
     * @param deck The deck of cards that the player has.
     */
    public void setDeck(ArrayList<Piece> deck) {
        this.deck = deck;
    }

    
    /**
     * This function returns the playerID of the player
     * 
     * @return The playerID
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * This function sets the playerID of the player to the value of the parameter playerID
     * 
     * @param playerID The ID of the player.
     */
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    /**
     * This function returns the number of passes a player has taken
     * 
     * @return passNum
     */
    public int getPassNum() {
        return passNum;
    }

    /**
     * This function sets the number of passes a player has taken
     * @param passNum The new number of passes a player has taken
     */
    public void setPassNum(int passNum) {
        this.passNum = passNum;
    }
    
    /**
     * This function creates a new player object with the same values as the original player object
     * 
     * @return A new player object with the same values as the original player object.
     */
    public Player clone(){
        return new Player(playerID, name, points, deck);
    }

    /**
     * This function takes the deck of pieces and adds the letter of each piece to a string for display
     * 
     * @return The deck of the player is being returned.
     */
    public String deckToString(){
        String deckString = "";
        //adds letter of each piece in player deck to the string
        for (Piece piece : deck){
            deckString += piece.getLetter().toUpperCase();
            deckString += " ";
        }
        //outputs with visually appealing formatting 
        return "[" + deckString.substring(0, deckString.length() - 1) + "]";
    }

    /**
     * This function draws a random number of pieces from the bag and adds them to the player's deck
     * 
     * @param gameboard the board object
     * @param bag a bag object
     */
    public void drawDeck(Board gameboard, Bag bag){
        Random rand = new Random();
        //calculates how many pieces need to be drawn
        int difference = 7 - deck.size();
        //adds and removes randomly drawn pieces from bag
        for (int i = 0; i < difference; i++){
            int piece = rand.nextInt(0, bag.getContents().size());
            deck.add(bag.getContents().get(piece));
            bag.getContents().remove(piece);
        }
    }


}
