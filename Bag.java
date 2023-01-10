import java.util.HashMap;
import java.util.ArrayList;

/**
 * The Bag class is used to generate the contents of the bag and to store the values of each letter.
 */
class Bag {
    private HashMap<String, Integer> values = new HashMap<String, Integer>();
    private ArrayList<Piece> contents = new ArrayList<Piece>();

    /* 
     * Constructor for the bag class. Creates a bag object
     * @param values hashmap for the point of each value
     * @param contents ArrayList of pieces that the bag contains
     */
    public Bag(HashMap<String, Integer> values, ArrayList<Piece> contents){
        this.contents = contents;
        this.values = values;
    }

    /**
     * This function returns the contents of the box
     * 
     * @return The contents of the ArrayList.
     */
    public ArrayList<Piece> getContents() {
        return contents;
    }

    /**
     * This function sets the contents of the cell to the contents of the parameter
     * 
     * @param contents An ArrayList of Piece objects.
     */
    public void setContents(ArrayList<Piece> contents) {
        this.contents = contents;
    }

    /**
     * This function gets the HashMap of String keys and point values
     * 
     * @return A HashMap of String and Integer.
     */
    public HashMap<String, Integer> getValues() {
        return values;
    }

    /**
     * This function takes a HashMap of String keys and Integer values and sets the values of the
     * current object to the values of the HashMap
     * 
     * @param values A HashMap of String keys and Integer values.
     */
    public void setValues(HashMap<String, Integer> values) {
        this.values = values;
    }

    /**
     * This function generates the values of each letter
     */
    public void generateValues(){
        //defining point count for each group of letters
        String onePoint = "AEIOULNSTR";
        String twoPoint = "DG";
        String threePoint = "BCMP";
        String fourPoint = "FHVWY";
        String fivePoint = "K";
        String eightPoint = "JX";
        String tenPoint = "QZ";
        //iterators through each string of letters and assigns values
        for (String letter : Scrabble.toStringArray(onePoint)){
            values.put(letter, 1);
        }
        for (String letter : Scrabble.toStringArray(twoPoint)){
            values.put(letter, 2);
        }
        for (String letter : Scrabble.toStringArray(threePoint)){
            values.put(letter, 3);
        }
        for (String letter : Scrabble.toStringArray(fourPoint)){
            values.put(letter, 4);
        }
        for (String letter : Scrabble.toStringArray(fivePoint)){
            values.put(letter, 5);
        }
        for (String letter : Scrabble.toStringArray(eightPoint)){
            values.put(letter, 8);
        }
        for (String letter : Scrabble.toStringArray(tenPoint)){
            values.put(letter, 10);
        }
        
    }

    /**
     * This function generates the contents of the bag by adding the correct amount of each letter to
     * the bag
     */
    public void generateContents(){
        ArrayList<String> alpha = Scrabble.toStringArray("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        int[] values = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
        int numberNum;
        //matches up amount of letters with each letter in the alphabet
        for (int i = 0; i < alpha.size(); i++){
            numberNum = values[i];
            for (int a = 0; a < numberNum; a++){
                contents.add(new Piece(alpha.get(i)));
            }
        }
    }

    

    
}
