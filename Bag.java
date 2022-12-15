import java.util.ArrayList;

class Bag {
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

    public void generateContents(){
        
    }

    
}
