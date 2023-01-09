import java.util.HashMap;
import java.util.ArrayList;

class Bag {
    HashMap<String, Integer> values = new HashMap<String, Integer>();
    ArrayList<Piece> contents = new ArrayList<Piece>();

    public Bag(HashMap<String, Integer> values, ArrayList<Piece> contents){
        this.contents = contents;
        this.values = values;
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
