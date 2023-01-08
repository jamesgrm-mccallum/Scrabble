import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.HashMap;

public class Scrabble{
    private static Scanner input = new Scanner(System.in);

    //TODO docstrings
    //TODO comment code

    // checks to see if word in word list
    private static String getInput(){
        
            //Variable for storing input
            String userInput = input.next();
            
            //returns input
            return userInput;
    }


    //place word returns void
    //takes in String word, String orientation, Coordinate starting_pos, String player
    //places down a word
    public static void placeWord(String word, String orientation, Coordinate start, player player, Board gameboard)throws InvalidOrientationException{

        boolean hasRemoved = false;

        int row = start.getY();
        int col = start.getX();

        for (String letter : toStringArray(word)){
            hasRemoved = false;
            gameboard.getTile()[row][col].setPiece(new Piece(letter));

            for (Iterator<Piece> it = player.getDeck().iterator(); it.hasNext();) {
                Piece p = it.next();
                if (p.getLetter().equals(letter.toUpperCase()) && hasRemoved == false) {
                    it.remove();
                    hasRemoved = true;
                }
            }

            if (orientation.equals("vertical")){
                row ++;
            }
            else if (orientation.equals("horizontal")){
                col ++;
            }
            else{
                throw new InvalidOrientationException();
            }
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
    public static int tallyWord(String orientation, String word, Coordinate start, Board gameboard, player player, Bag bag){
        int word_score = 0;
        int lettermultiplier = 1;
        int wordmultiplier = 1;

        int row = start.getY();
        int col = start.getX();

        for (String letter : toStringArray(word)){
            Tile currentTile = gameboard.getTile()[row][col];
            if (currentTile.getType() != null){
                if (currentTile.getType() == "2L"){
                    lettermultiplier = 2;
                }
                else if (currentTile.getType() == "2W"){
                    wordmultiplier = wordmultiplier * 2;
                }
                else if(currentTile.getType() == "3L"){
                    lettermultiplier = 3;
                }
                else if (currentTile.getType() == "3W"){
                    wordmultiplier = wordmultiplier * 3;
                }
            }
            word_score += bag.getValues().get(letter.toUpperCase()) * lettermultiplier;
            lettermultiplier = 1;

            if (orientation.equals(("vertical"))){
                row ++;
            }
            else if (orientation.equals("horizontal")){
                col++;
            }
        }

        if (word.length() == 7){
            word_score += 50;
        }
        
        return word_score * wordmultiplier;
    }

    public static Coordinate getStartFromLetter(String word, String orientation, String letter, Coordinate letterCord, Board gameboard){
        System.out.println(letterCord);
        int col = letterCord.getX();
        int row = letterCord.getY();

        if (orientation.equals("vertical")){
            while (gameboard.getTile()[row][col].getPiece() != null && row >= 0){
                row--;
            }
            return new Coordinate(col, row + 1);
        }
        else{
            while (gameboard.getTile()[row][col].getPiece() != null && col >= 0){
                col--;
            }
            return new Coordinate(col + 1, row);
        }
    }

    public static String oppositeOrientation(String orientation){
        if (orientation.equals("vertical")){
            return "horizontal";
        }
        else{
            return "vertical";
        }
    }

    //tallyPlay
    public static int tallyPlay(String word, Coordinate start, String orientation, player player, Board gameboard, Bag bag){
        int playScore = 0;

        int row = start.getY();
        int col = start.getX();

        for (String letter : toStringArray(word)){
            Coordinate letterCord = new Coordinate(col, row);
            String[] connections;
            
            if (orientation.equals("vertical")){
                if (row - start.getY() == word.length()){
                    connections = Validator.findConnections(letter, getStartFromLetter(word, oppositeOrientation(orientation), letter, letterCord, gameboard), "last", player, gameboard);
                }
                else{
                    connections = Validator.findConnections(letter, getStartFromLetter(word, oppositeOrientation(orientation), letter, letterCord, gameboard), orientation, player, gameboard);
                }
                
                if (row == (start.getY() + word.length())){
                    playScore += tallyWord(orientation, connections[0], start, gameboard, player, bag);
                    playScore += tallyWord(orientation, connections[1], start, gameboard, player, bag);
                }
                else {
                    playScore += tallyWord(orientation, connections[0], start, gameboard, player, bag);
                }
                row++;
            }
            else if(orientation.equals("horizontal")){
                if (col - word.length() == start.getX() - 1){
                    connections = Validator.findConnections(letter, getStartFromLetter(word, oppositeOrientation(orientation), letter, letterCord, gameboard), "last", player, gameboard);
                }
                else {
                    connections = Validator.findConnections(letter, getStartFromLetter(word, oppositeOrientation(orientation), letter, letterCord, gameboard), orientation, player, gameboard);
                }
                if (col - word.length() == start.getX() - 1){
                    playScore += tallyWord(orientation, connections[0], start, gameboard, player, bag);

                    playScore += tallyWord(orientation, connections[1], start, gameboard, player, bag);
                }
                else {
                    playScore += tallyWord(orientation, connections[1], start, gameboard, player, bag);
                }
                col++;
            }
        }   

        return playScore;
    }

    public static void playerMenu(player player, Board gameboard){
        System.out.println(gameboard.toString());
        System.out.println(player.getName() + "\'s turn!" );
        System.out.println(player.deckToString());
        System.out.println("\n1. Play\n2. Exchange\n3. Pass");
    }

    public static void exchangetiles(player player, Bag bag) {
        System.out.println("Enter pieces to replace: ");
        String replacePieces = getInput().replace(" ", "");

        ArrayList<Integer> replacePiecesList = new ArrayList<Integer>();      
        for (int i = 0; i < replacePieces.length(); i ++){
            replacePiecesList.add(Integer.valueOf(replacePieces.substring(i, i + 1)));
        }

        Random rand = new Random();
        for (int piece : replacePiecesList){
            piece = piece - 1;
            int randint = rand.nextInt(0, bag.getContents().size());
            Piece randPiece = bag.getContents().get(randint);
            player.getDeck().set(piece, randPiece);
            bag.getContents().remove(randint);
        }
    }


    public static int getHighScore(){
        int score = 0;
        try {
            File file = new File("highscore.txt");
            Scanner input = new Scanner(file);
            String last = ""; 
            while (input.hasNextLine()) {
                last = input.nextLine();
            }
            String replacedLine = last.replace(" ", "");
            int hyphinIndex = replacedLine.indexOf("-");
            input.close();
            score = Integer.valueOf(replacedLine.substring(hyphinIndex + 1));
        }
        catch (FileNotFoundException e){
            System.out.println("File does not exist.");
        }
        return score;
    }

    public static void newHighScore(player player){
        if (player.getPoints() > getHighScore()){
            try{
                File file = new File("highscore.txt");
                FileWriter writer = new FileWriter(file, true);
                writer.write("\n" + player.getName() + " - " + String.valueOf(player.getPoints()));
                writer.close();
            }
            catch (IOException e) {
                System.out.println("File cannot be opened.");
            }
        }
    }


    public static String getPlayerName(String ID){
        System.out.printf("Enter player %s's name:", ID);
        String name = getInput();
        return name;
    }
    

    public static void playWord(player player, Board gameboard, Bag bag, boolean isFirstTurn){
        while (true){
            try {
                System.out.println("Enter word:");
                String word = getInput();
    
                System.out.println("Enter Orientation (vertical/horizontal):");
                String orientation = getInput();
    
                System.out.println("Enter start Coordinate: ");
                Coordinate start = Coordinate.translateString(getInput());
                
                if (Validator.validateInput(word, orientation, start, player, gameboard, isFirstTurn)){
                    placeWord(word, orientation, start, player, gameboard);
                    System.out.printf("That play was worth %s points!", Integer.toString(tallyPlay(word, start, orientation, player, gameboard, bag)));
                    player.setPoints(player.getPoints() + tallyPlay(word, start, orientation, player, gameboard, bag));
                }
                break;
            } 
            catch(WordNotConnected e){
                System.out.println("Word is not connected!");
            }
            catch (WordNotFoundException e){
                System.out.println("Word not found within dictionary!");
            }
            catch (WordNotWithinRangeException e){
                System.out.println("Word is outside of board bounds!");
            }
            catch (InvalidOrientationException e){
                System.out.println("Word orientation must be either vertical or horizontal!");
            }
            catch (WordNotInDeckException e){
                System.out.println("Word is not in Deck!");
            }
            catch (InvalidConnectionException e){
                System.out.println("Word has invalid connections!");
            }
            catch(InvalidCoordinateException e){
                System.out.println("Invalid Coordinate!");
            }
        }
        
    }


    public static void turn(player player, Board gameboard, Bag bag, boolean isFirstTurn)throws IllegalArgumentException{
        playerMenu(player, gameboard);
        int choice = Integer.valueOf(getInput());
        switch(choice){
            case 1:
                playWord(player, gameboard, bag, isFirstTurn);
                player.setPassNum(0);
                break;
            case 2:
                exchangetiles(player, bag);
                player.setPassNum(0);
                break;
            case 3:
                player.setPassNum(player.getPassNum() + 1);
                break;
            default:
                throw new IllegalArgumentException(); //TODO add ChoiceOutofBoundsException
        }
        if (bag.getContents().size() > 0){
            player.drawDeck(gameboard, bag);
        }
    }


    public static void main(String[] args) {
        //creating objects
        Board gameboard = new Board();
        Bag bag = new Bag(new HashMap<String, Integer>(), new ArrayList<Piece>());
        String player1_name = getPlayerName("1");
        player player1 = new player(0, player1_name, 0, new ArrayList<Piece>());
        String player2_name = getPlayerName("2");
        player player2 = new player(0, player2_name, 0, new ArrayList<Piece>());
        
        //generating shit
        gameboard.generateBoard();
        bag.generateContents();
        bag.generateValues();
        player1.drawDeck(gameboard, bag);
        player2.drawDeck(gameboard, bag);

        boolean isPlayerOneTurn = true;
        boolean isFirstTurn = true;

        while (player1.getPassNum() < 2 && player2.getPassNum() < 2){
            if (isPlayerOneTurn){
                turn(player1, gameboard, bag, isFirstTurn);
                isPlayerOneTurn = false;
            }
            else {
                turn(player2, gameboard, bag, isFirstTurn);
                isPlayerOneTurn = true;
            }
            isFirstTurn = false;
        }
    }
}