import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class is used to validate the input of the user
 */
public class Validator {
    //global instance variable used to keep track of words already used
    private static ArrayList<String> wordsUsed;

    /**
     * This function checks if the word is within the board
     * 
     * @param word the word that the player is trying to place
     * @param direction "vertical" or "horizontal"
     * @param start the starting coordinate of the word
     * @param player the player who is placing the word
     * @return A boolean value
     */
    public static boolean withinBoard(String word, String direction, Coordinate start, player player){
        if (start.getX() <= 14 && start.getX() >= 0 && start.getY() <= 14 && start.getY() >= 0){
            // logic for getting endpoint of word
            Coordinate endPoint;

            if (direction.equals("vertical")){
                endPoint = new Coordinate(start.getX(), start.getY() + word.length());
            }
            else {
                endPoint = new Coordinate(start.getX() + word.length(), start.getY());
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

    /**
     * This function checks if the word has already been used
     * 
     * @param word the word that is being checked
     * @return The method is returning a boolean value.
     */
    public static boolean wordNotUsed(String word){
        for (int i = 0; i < wordsUsed.size(); i++){
            //checks if word has already been used
            if (word.equalsIgnoreCase(wordsUsed.get(i))){
                return false;
            }
        }
        return true;
    }
    

    
    /**
     * Method for ensuring that a play is a valid one
     * 
     * @param word the word that the player is trying to place
     * @param direction the direction the word is being placed in
     * @param start the starting coordinate of the word
     * @param player the player who is playing the word
     * @param gameboard the board that the game is being played on
     * @param isFirstTurn boolean that is true if it is the first turn of the game
     * @return The method is returning a boolean value.
     * @throws WordNotWithinRangeException if the word isn't on the board
     * @throws WordNotFoundException if the word isn't in the word dictionary file
     * @throws InvalidOrientationException if the word doesn't have a valid orietation
     * @throws WordNotInDeckException if the player doesnt have the nessecary pieces to place the word 
     * @throws WordNotConnectedException if the word isn't connected to an adjacent word
     * @throws WordAlreadyUsedException if the word has already been used
     * @throws NotOnStarException if the first word isn't placed on the star
     * 
     */
    public static boolean validateInput(String word, String direction, Coordinate start, player player, Board gameboard, boolean isFirstTurn)throws WordNotWithinRangeException, WordNotFoundException, InvalidOrientationException, WordNotInDeckException, WordNotConnectedException, WordAlreadyUsedException, NotOnStarException{
        if (isFirstTurn){
            if (! isOnStar(word, direction, start, gameboard)){
                throw new NotOnStarException();
            }
        }
        // checks that word is a word in the dictionary
        if (isWord(word)){
            //Checks that the word has the correct pieces to be placed
            if (validLetter(word, direction, start, player, gameboard) == word.length()){
                //Checks that start point is within board
                if (withinBoard(word, direction, start, player)){
                    //checks that all the connections that a word makes are valid
                    if (validateWordConnection(word, direction, start, player, gameboard, isFirstTurn)){
                        //checks that a word hasn't already been used
                        if (wordNotUsed(word)){
                            wordsUsed.add(0, word);
                            return true;
                        }
                        else {
                            throw new WordAlreadyUsedException();
                        }
                    }
                    else {
                        throw new WordNotConnectedException();
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

    /**
     * This function checks if the first word is on a star tile
     * 
     * @param word the word that is being placed
     * @param direction "vertical" or "horizontal"
     * @param start the starting coordinate of the word
     * @param gameboard a 2D array of tiles
     * @return A boolean value
     */
    public static boolean isOnStar(String word, String direction, Coordinate start, Board gameboard){
        //iterators through all tiles the word is on and checks that it has a star
        // for vertical word
        if (direction.equals("vertical")){
            for (int i = start.getY(); i < start.getY() + word.length(); i++){
                if (gameboard.getTile()[i][start.getX()].getType().equals("STAR")){
                    return true;
                }
            }
        }
        //for horizontal word
        else {
            for (int i = start.getX(); i < start.getX() + word.length(); i++){
                if (gameboard.getTile()[start.getY()][i].getType().equals("STAR")){
                    return true;
                }
            }
        }

        return false;

        
    }


    /**
     * It checks if the letter is connected to a word
     * 
     * @param letter the letter that is being placed
     * @param letterCord the coordinate of the letter
     * @param orientation "horizontal" or "vertical"
     * @param player the player who is playing the turn
     * @param gameboard the board object
     * @return The method is returning a string.
     */
    public static String validateLetterConnection(String letter, Coordinate letterCord, String orientation, player player, Board gameboard){
        //gets the connections of a letter
        String[] connections = findConnections(letter, letterCord, orientation, player, gameboard);

        //checking if it is connected vertically
        if (connections[0].length() > 0){
            if (isWord(connections[0])){
                return "connected";
            }
        }

        //checking if the letter is connected horizontally
        if (connections[1].length() > 0){
            if (isWord(connections[1])){
                return "connected";
            }
        }
        
        return "empty";
        
    }

    /**
     * checks that all the letters in a word are connected properly
     * 
     * @param word the word that is being placed on the board
     * @param direction "vertical" or "horizontal"
     * @param start the starting coordinate of the word
     * @param player the player who is playing the word
     * @param gameboard the board object
     * @param isFirstTurn boolean
     * @return true if the word is connected properly, false otherwise
     */
    public static boolean validateWordConnection(String word, String direction, Coordinate start, player player, Board gameboard, boolean isFirstTurn){
        //converts word to list of characters (as strings)
        ArrayList<String> wordList = Scrabble.toStringArray(word);
        int connectedCount = 0;
        String letterConnectionState = "";
        for (int i = 0; i < word.length(); i++){
            //validates the connection of each letter in the world
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

        //changes result based on status of frist turn
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

    /**
     * This function checks that the letters used in the word are in the player's deck
     * 
     * @param word the word that the player is trying to place
     * @param orientation "vertical" or "horizontal"
     * @param start the coordinate of the first letter of the word
     * @param player the player who is placing the word
     * @param gameboard the board object
     * @return The number of letters that are valid.
     */
    public static Integer validLetter(String word, String orientation, Coordinate start, player player, Board gameboard){
        int iterations = 0;
        int row = start.getY();
        int col = start.getX();
        String accum = "";

        
        //Gets the letters already placed on word that the currently placed word is going to interact with
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
        //recreated deck as arraylist
        ArrayList<String> deckList = new ArrayList<String>();
        for (Piece p: player.getDeck()){
            deckList.add(p.getLetter());
        }

        String deckString = String.join("", deckList);
        ArrayList<String> accumList = new ArrayList<String>(Arrays.asList(accum.split("")));
        

        //checking that all used letters are in deck
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


    /**
     * 
     * returns an array the two connections a letter has, one for the vertical word and one for the horizontal connection
     * 
     * @param letter the letter that is being placed on the board
     * @param letterCord The coordinate of the letter that is being placed
     * @param orientation "vertical" or "horizontal"
     * @param player the player who is playing the turn
     * @param gameboard the board object
     * @return an array containing the connection of the letter on both axis
     */
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

    /**
     * This function takes in a string and checks if it is in the file
     * 
     * @param word the word to be checked
     * @return The method is returning a boolean value.
     */
    public static boolean isWord(String word){
        //checks that word is in the file
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
        //error handling for when file cannot be found
        catch (FileNotFoundException e){
            System.out.println("File not found!");
            
        }
        return false;

    }

}
