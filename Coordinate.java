import java.util.ArrayList;

class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    @Override
    public String toString(){
        return "(" + String.valueOf(x) + ", " + String.valueOf(y) + ")";
    }

    public boolean equals(Coordinate other){
        if (x == other.getX() && y == other.getY()){
            return true;
        }
        else{
            return false;
        }
    }

    

    public static Coordinate translateString(String expr) throws InvalidCoordinateException{
        ArrayList<String> alpha = Scrabble.toStringArray("ABCDEFGHIJKLMNO");
        if (expr.matches("[A-Z][0-9][0-9]*")){ 
            int x = 0;
            int y = Integer.valueOf(expr.substring(1));
            for (int i = 0; i < alpha.size(); i++){
                if (expr.substring(0, 1).equals(alpha.get(i))){
                    x = i + 1;
                    break;
                }
                
            }
            return new Coordinate(x - 1, y - 1);
        }
        else {
            throw new InvalidCoordinateException();
        }
        
    }

    @Override
    public Coordinate clone(){
        return new Coordinate(x, y);
    }

}