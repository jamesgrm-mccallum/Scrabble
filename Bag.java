import java.util.HashMap;
import java.util.ArrayList;

class Bag {
    HashMap<String, Integer> values = new HashMap<String, Integer>();
    ArrayList<Piece> contents = new ArrayList<Piece>();

    public Bag(ArrayList<Piece> contents){
        this.contents = contents;
    }

    public ArrayList<Piece> getContents() {
        return contents;
    }

    public void setContents(ArrayList<Piece> contents) {
        this.contents = contents;
    }

    public HashMap<String, Integer> getValues() {
        return values;
    }

    public void setValues(HashMap<String, Integer> values) {
        this.values = values;
    }

    public void generateValues(){
        String onePoint = "AEIOULNSTR";
        String twoPoint = "DG";
        String threePoint = "BCMP";
        String fourPoint = "FHVWY";
        String fivePoint = "K";
        String eightPoint = "JX";
        String tenPoint = "QZ";
        
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

    

    
}
