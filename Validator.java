import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Validator {

    public static boolean withinBoard(String word, String direction, Coordinate start, player player) throws InvalidOrientationException{
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
                throw new InvalidOrientationException();
            }

            //Checks that end point is within board
            if (endPoint.getX() <= 14 && endPoint.getX() >= 0 && endPoint.getY() <= 14 && endPoint.getY() >= 0){
                return true;
            }
            else {
                return false;
            }
        }
        else{
            return false;
        }
    }
    

    
    public static boolean validateInput(String word, String direction, Coordinate start, player player, Board gameboard, boolean isFirstTurn)throws IllegalArgumentException, WordNotWithinRangeException, WordNotFoundException, InvalidOrientationException, WordNotInDeckException, InvalidConnectionException, WordNotConnected{
        // checks that word is a word in the dictionary
        if (isWord(word)){
            //Checks that the word has the correct pieces to be placed
            if (validLetter(word, direction, start, player, gameboard) == word.length()){
                //Checks that start point is within board
                if (withinBoard(word, direction, start, player)){
                    if (validateWordConnection(word, direction, start, player, gameboard, isFirstTurn)){
                        return true;
                    }
                    else {
                        throw new WordNotConnected();
                    }
                }
                else{
                    throw new WordNotWithinRangeException();
                }
            }
            else {
                throw new WordNotInDeckException();
            }
        }
        else {
            throw new WordNotFoundException();
        }
    }


    public static String validateLetterConnection(String letter, Coordinate letterCord, String orientation, player player, Board gameboard){
        String[] connections = findConnections(letter, letterCord, orientation, player, gameboard);

        if (connections[0].length() > 0){
            if (isWord(connections[0])){
                return "connected";
            }
        }

        if (connections[1].length() > 0){
            if (isWord(connections[1])){
                return "connected";
            }
        }
        
        return "empty";
        
    }

    public static boolean validateWordConnection(String word, String direction, Coordinate start, player player, Board gameboard, boolean isFirstTurn){
        ArrayList<String> wordList = Scrabble.toStringArray(word);
        int connectedCount = 0;
        String letterConnectionState = "";
        for (int i = 0; i < word.length(); i++){
            if (direction.equalsIgnoreCase("vertical")){
                letterConnectionState = validateLetterConnection(wordList.get(i), new Coordinate(start.getX(),start.getY() + i) , direction, player, gameboard);
            }
            else if (direction.equalsIgnoreCase("horizontal")){
                letterConnectionState = validateLetterConnection(wordList.get(i), new Coordinate(start.getX() + i,start.getY()) , direction, player, gameboard);
            }

            if (letterConnectionState.equals("connected")){
                connectedCount++;
            }
        }

        if (isFirstTurn && connectedCount == 0){
            return true;
        }
        else if (isFirstTurn == false && connectedCount == word.length()){
            return true;
        }
        else{
            return false;
        }
    }

    public static Integer validLetter(String word, String orientation, Coordinate start, player player, Board gameboard){
        int iterations = 0;
        int row = start.getY();
        int col = start.getX();
        String accum = "";

        

        while (iterations < word.length()){
            Piece current_piece = gameboard.getTile()[row][col].getPiece();
            if (current_piece == null){
                accum += " ";
            }
            else {
                accum += current_piece.getLetter();
            }

            if (orientation.equals("vertical")){
                row++;
            }
            else {
                col++;
            }
            iterations ++;
        }


        int correct = 0;
        ArrayList<String> wordList = new ArrayList<String>(Arrays.asList(word.split("")));
        ArrayList<String> deckList = new ArrayList<String>();
        for (Piece p: player.getDeck()){
            deckList.add(p.getLetter());
        }
        String deckString = String.join("", deckList);
        ArrayList<String> accumList = new ArrayList<String>(Arrays.asList(accum.split("")));
        

        
        for (int i = 0; i < wordList.size(); i++){
            for (int x = 0; x < deckList.size(); x ++){
                if (wordList.get(i).equalsIgnoreCase(accumList.get(i))){
                    correct ++;
                    x = deckList.size();
                }
                else if (wordList.get(i).equalsIgnoreCase(deckList.get(x))){
                    deckList.remove(x);
                    correct ++;
                    x = deckList.size();
                }
                
                if (! deckString.contains(wordList.get(i)) && accum.contains(wordList.get(i))){
                    if (deckList.contains(".")){
                        deckList.remove(deckList.indexOf("."));
                        correct++;
                    }
                }
            }
        }

        return correct;
        
    }


    public static String[] findConnections(String letter, Coordinate letterCord, String orientation, player player, Board gameboard){
        String wordVertical = "";
        String wordHorizontal = "";
        String right = "";
        String left = "";
        String up = "";
        String down = "";
        
        if (orientation.equals("vertical") || orientation.equals("last")){
            if (gameboard.getTile()[letterCord.getY()][letterCord.getX() - 1].getPiece() != null){
                int decreaseCord = 1;
                while (gameboard.getTile()[letterCord.getY()][letterCord.getX() - decreaseCord].getPiece() != null && letterCord.getX() - decreaseCord >= 0){
                    left += gameboard.getTile()[letterCord.getY()][letterCord.getX() - decreaseCord].getPiece().getLetter();
                    decreaseCord ++;
                }
            }
            //getting the right below the letter
            if (gameboard.getTile()[letterCord.getY()][letterCord.getX() + 1].getPiece() != null){
                int increaseCord = 1;
                while (gameboard.getTile()[letterCord.getY()][letterCord.getX() + increaseCord].getPiece() != null && letterCord.getX() + increaseCord <= 14){
                    right += gameboard.getTile()[letterCord.getY()][letterCord.getX() + increaseCord].getPiece().getLetter();
                    increaseCord ++;
                }
            }
        }
        if (orientation.equals("horizontal") || orientation.equals("last")){
            //getting the letters to the top of the letter
            if (gameboard.getTile()[letterCord.getY() - 1][letterCord.getX()].getPiece() != null){
                int leftCord = 1;
                while(gameboard.getTile()[letterCord.getY() - leftCord][letterCord.getX()].getPiece() != null && letterCord.getY() - leftCord >= 0){
                    up += gameboard.getTile()[letterCord.getY() - leftCord][letterCord.getX()].getPiece().getLetter();
                    leftCord ++;
                }
            }
            //getting the letters to the bottum of the letter
            if (gameboard.getTile()[letterCord.getY() + 1][letterCord.getX()].getPiece() != null){
                int rightCord = 1;
                while(gameboard.getTile()[letterCord.getY() + rightCord][letterCord.getX()].getPiece() != null && letterCord.getY() + rightCord <= 14){
                    down += gameboard.getTile()[letterCord.getY() + rightCord][letterCord.getX()].getPiece().getLetter();
                    rightCord ++;
                }
            }
        }

        //Compiling words and checking if they are in dictionary

        if (up.length() > 0 || down.length() > 0){
            wordVertical = new StringBuffer(up).reverse().toString() + letter + down;
        }
        if (left.length() > 0 || right.length() > 0){
            wordHorizontal = new StringBuffer(left).reverse().toString() + letter + right;
        }
        

        return new String[]{wordVertical, wordHorizontal};
    }

    public static boolean isWord(String word){
        
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
}
