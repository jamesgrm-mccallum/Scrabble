import java.util.HashMap;
import java.util.ArrayList;

class Bag {
    HashMap<Integer, String> values = new HashMap<Integer, String>();
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

    public void generateValues(){
        String onePoint = "AEIOULNSTR";
        String twoPoint = "DG";
        String threePoint = "BCMP";
        String fourPoint = "FHVWY";
        String fivePoint = "K";
        String eightPoint = "JX";
        String tenPoint = "QZ";
        
        for (String letter : Scrabble.toStringArray(onePoint)){
            values.put(1, letter);
        }
        for (String letter : Scrabble.toStringArray(twoPoint)){
            values.put(2, letter);
        }
        for (String letter : Scrabble.toStringArray(threePoint)){
            values.put(3, letter);
        }
        for (String letter : Scrabble.toStringArray(fourPoint)){
            values.put(4, letter);
        }
        for (String letter : Scrabble.toStringArray(fivePoint)){
            values.put(5, letter);
        }
        for (String letter : Scrabble.toStringArray(eightPoint)){
            values.put(8, letter);
        }
        for (String letter : Scrabble.toStringArray(tenPoint)){
            values.put(10, letter);
        }
        
    }

    
}
