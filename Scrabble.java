import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Scrabble{

    // checks to see if word in word list

    public boolean isWord(String word){
        
        try{
            File file = new File("words.txt");
            Scanner input = new Scanner(file);
            while (input.hasNextLine()){
                String fileWord = input.nextLine();
                if (fileWord.toUpperCase().equals(word.toUpperCase())){
                    return true;
                }
            }
            input.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
            
        }
        return false;

    }


    public boolean validateConnection(String letter, Coordinate letterCord, player player, Board gameboard){
        String wordVertical = "";
        String wordHorizontal = "";
        String right = "";
        String left = "";
        String up = "";
        String down = "";
        
        //getting the letters to the left of the letter
        if (gameboard.getTile()[letterCord.getY() - 1][letterCord.getX()].getPiece() != null){
            int leftCord = 1;
            while(gameboard.getTile()[letterCord.getY() - leftCord][letterCord.getX()].getPiece() != null && letterCord.getY() - leftCord >= 0){
                left += gameboard.getTile()[letterCord.getY() - leftCord][letterCord.getX()].getPiece().getLetter();
                leftCord ++;
            }
        }
        //getting the letters to the right of the letter
        if (gameboard.getTile()[letterCord.getY() + 1][letterCord.getX()] != null){
            int rightCord = 1;
            while(gameboard.getTile()[letterCord.getY() + rightCord][letterCord.getX()].getPiece() != null && letterCord.getY() + rightCord <= 14){
                right += gameboard.getTile()[letterCord.getY() + rightCord][letterCord.getX()].getPiece().getLetter();
                rightCord ++;
            }
        }
        //getting the letters above the letter
        if (gameboard.getTile()[letterCord.getY()][letterCord.getX() - 1] != null){
            int decreaseCord = 1;
            while (gameboard.getTile()[letterCord.getY()][letterCord.getX() - decreaseCord] != null && letterCord.getX() - decreaseCord >= 0){
                up += gameboard.getTile()[letterCord.getY()][letterCord.getX() - decreaseCord];
                decreaseCord ++;
            }
        }
        //getting the letters below the letter
        if (gameboard.getTile()[letterCord.getY()][letterCord.getX() + 1] != null){
            int increaseCord = 1;
            while (gameboard.getTile()[letterCord.getY()][letterCord.getX() + increaseCord] != null && letterCord.getX() + increaseCord <= 14){
                down += gameboard.getTile()[letterCord.getY()][letterCord.getX() + increaseCord];
                increaseCord ++;
            }
        }
        
        //Compiling words and checking if they are in dictionary
        wordVertical = up + letter + down;
        wordHorizontal = left + letter + right;
        if (isWord(wordVertical) && isWord(wordHorizontal)){
            return true;
        }
        else{
            return false;
        }
    }

    //validate word returns boolean
    //takes in: String word, String orientation, Coordinate starting pos, String player
    //checks to see if a word is a real word, player has correct pieces for word, word can fit on board

    public boolean validateInput(String word, String direction, Coordinate start, player player, Board gameboard)throws IllegalArgumentException{
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
                        if (direction.equals("vertical")){
                            int i = start.getY();
                            for (String letter : wordList){
                                if (!validateConnection(letter, new Coordinate(start.getX(), i + 1 ), player, gameboard)){
                                    return false;
                                }
                            }
                            i++;
                        }
                        else {
                            int i = start.getX();
                            for (String letter : wordList){
                                if (!validateConnection(letter, new Coordinate(i + 1, start.getY()), player, gameboard)){
                                    return false;
                                }
                            }
                            i++;
                        }
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
        boolean hasRemoved = false;
        if (orientation.equals("vertical")){
            int i = start.getY();
            for (String letter : wordList){
                hasRemoved = false;
                gameboard.getTile()[i - 1][start.getX()- 1].setPiece(new Piece(letter));
                
                for (Iterator<Piece> it = player.getDeck().iterator(); it.hasNext();) {
                    Piece p = it.next();
                    if (p.getLetter().equals(letter.toUpperCase()) && hasRemoved == false) {
                        it.remove();
                        hasRemoved = true;
                    }
                }
                i++;
           }  
        }
        
        else if (orientation.equals("horizontal")){
            int i = start.getX();
            for (String letter : wordList){
                
                gameboard.getTile()[start.getY() - 1][i - 1].setPiece(new Piece(letter));
                for (Iterator<Piece> it = player.getDeck().iterator(); it.hasNext();) {
                    Piece p = it.next();
                    if (p.getLetter().toUpperCase().equals(letter.toUpperCase()) && hasRemoved == false) {
                        System.out.println("HI");
                        it.remove();
                        hasRemoved = true;
                    }
                }
                
                i++;
           }  
        }
        else{
            throw new IllegalArgumentException("Orientation must be either horizontal or vertical");
        }
    }
    
    public static ArrayList<String> toStringArray(String expr){
        ArrayList<String> wordArray = new ArrayList<String>();
        for (int i = 0; i < expr.length(); i++){
            wordArray.add(expr.substring(i, i + 1));
        }
        return wordArray;
    } 
    
    //tallyWord
    public int tallyWord(String orientation, String word, Coordinate start, Board gameboard, player player){
        int word_score = 0;
        int lettermultiplier = 1;
        int mulitplier = 1;

        if (orientation.equals("vertical")){
            int i = start.getY();
            for (String letter : toStringArray(word)){
                Tile currentTile = gameboard.getTile()[i][start.getX()];

            }
        }
        else if(orientation.equals("horizontal")){
            int i = start.getX();
            for (String letter : toStringArray(word)){

            }
        }
    }

    //tallyPlay




    public static void main(String[] args) {
        
    }
}