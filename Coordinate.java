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

    public static Coordinate translateString(String expr) throws IllegalArgumentException{
        int x = 0;
        int y = Integer.valueOf(expr.substring(1));
        ArrayList<String> alpha = Scrabble.toStringArray("ABCDEFGHIJKLMNO");
        if (expr.matches("[A-Z][0-9][0-9]*")){ //FIXME regex doesn't work
            for (int i = 0; i < alpha.size(); i++){
                if (expr.substring(0, 1).equals(alpha.get(i))){
                    x = i + 1;
                    break;
                }
            }
        }
        else {
            throw new IllegalArgumentException("Enter Coordinate format Invalid"); // TODO InvalidCoordinateException
        }
        return new Coordinate(x - 1, y - 1);
    }

}