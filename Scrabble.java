import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
public class Scrabble{

    // checks to see if word in word list

    public boolean isWord(String word){
        return true;
    }

    //validate word returns boolean
    //takes in: String word, String orientation, Coordinate starting pos, String player
    //checks to see if a word is a real word, player has correct pieces for word, word can fit on board

    public boolean validateInput(String word, String direction, Coordinate start, player player)throws IllegalArgumentException{
        // checks that word is a word in the dictionary
        if (isWord(word)){
            // logic for getting sorted strings of player deck and word
            ArrayList<String> wordList = new ArrayList<String>(Arrays.asList(word.split("")));
            ArrayList<String> deckList = new ArrayList<String>();
            for (Piece p: player.getDeck()){
                deckList.add(p.getLetter());
            }
            //Sorting both lists
            Collections.sort(wordList);
            Collections.sort(deckList);

            //turning sorted lists into strings
            String wordString = String.join("", wordList);
            String deckString = String.join("", deckList);
            //Checks that the word has the correct pieces to be placed
            if (deckString.contains(wordString)){
                //Checks that start point is within board
                if (start.getX() <= 14 && start.getX() >= 0 && start.getY() <= 14 && start.getY() >= 0){
                    // logic for getting endpoint of word
                    Coordinate endPoint;

                    if (direction.equals("vertical")){
                        endPoint = new Coordinate(start.getX(), start.getY() + word.length());
                    }
                    else if (direction.equals("horizontal")){
                        endPoint = new Coordinate(start.getX() + word.length(), start.getY());
                    }
                    else {
                        throw new IllegalArgumentException("Direction must be vertical or horizontal!");
                    }

                    //Checks that end point is within board
                    if (endPoint.getX() <= 14 && endPoint.getX() >= 0 && endPoint.getY() <= 14 && endPoint.getY() >= 0){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    return false;
                }
            }
            else {
                return false;
            }

        }
        else {
            return false;
        }
    }

    //place word returns void
    //takes in String word, String orientation, Coordinate starting_pos, String player
    //places down a word
    
    public static void placeWord(String word, String orientation, Coordinate start, player player, Board gameboard)throws IllegalArgumentException{

        ArrayList<String> wordList = new ArrayList<String>(Arrays.asList(word.split("")));
        if (orientation.equals("vertical")){
           for (String letter : wordList){
                for (int i = start.getY(); i <= start.getY() + word.length(); i ++){
                    if (gameboard.getTile()[i][start.getX()].getPiece() == null){
                        gameboard.getTile()[i][start.getX()].setPiece(new Piece(letter));
                        for (Piece p : player.getDeck()){
                            if (p.getLetter() == letter){
                                player.getDeck().remove(p);
                            }
                        }
                    }
                    else {
                        if (gameboard.getTile()[i][start.getX()].getPiece().getLetter() == letter){

                        }
                        else {
                            throw new IllegalArgumentException("Path is obstructed!"); //CHANGE THIS
                        }
                    }
                }
           } 
        }
        else if (orientation.equals("vertical")){
            for (String letter : wordList){
                for (int i = start.getX(); i <= start.getX() + word.length(); i ++){
                    if (gameboard.getTile()[start.getY()][i].getPiece() == null){
                        gameboard.getTile()[start.getY()][i].setPiece(new Piece(letter));
                        for (Piece p : player.getDeck()){
                            if (p.getLetter() == letter){
                                player.getDeck().remove(p);
                            }
                        }
                    }
                    else {
                        if (gameboard.getTile()[i][start.getX()].getPiece().getLetter() != letter){
                            throw new IllegalArgumentException("Path is obstructed!");
                        }
                    }
                }
           }  
        }
    }




    public static void main(String[] args) {
        
    }
}