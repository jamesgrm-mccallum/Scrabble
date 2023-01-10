/* 
 * PROJECT: CHECKERS
 * CREATED BY: JAMES MCCALLUM
 * FILES:
 *  - Bag.java
 *  - Board.java
 *  - ChoiceOutofBoundsException.java
 *  - Coordinate.java
 *  - highscore.txt
 *  - InvalidConnectionException.java
 *  - InvalidCoordinateException.java
 *  - InvalidExchangeFormatException.java
 *  - InvalidOrienationException.java
 *  - NotOnStarException.java
 *  - Piece.java
 *  - player.java
 *  - Scorer.java
 *  - Scrabble.java
 *  - Scrabble.pdf
 *  - Tile,java
 *  - Validator.java
 *  - WordAlreadyUsedException.java
 *  - WordNotConnectedException.java
 *  - WordNotFoundException.java
 *  - WordNotInDeckException.java
 *  - WordNotWithinrangeException.java
 *  - ScrabbleUML.pdf
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.HashMap;

/**
 * This class is the main class for the game, it contains the main method, and all the miscellaneous methods for the game
 * 
 */
class Scrabble{
    private static Scanner input = new Scanner(System.in);


    public static void intro(){
        System.out.println("Welcome to Scrabble!\n Please Find the rules at https://www.scrabblepages.com/scrabble/rules/");
        System.out.println("This was created by James McCallum!");
    }


    /**
     * This function takes in input from the user and returns it as a string
     * 
     * @return The user input
     */
    private static String getInput(){
            //Variable for storing input
            String userInput = input.nextLine();
            
            //returns input
            return userInput;
    }
    

    
    /**
     * This function takes a word, an orientation, a starting coordinate, a player, and a gameboard as
     * parameters and places the word on the gameboard
     * 
     * @param word the word that is being placed
     * @param orientation "vertical" or "horizontal"
     * @param start The coordinate of the first letter of the word
     * @param player The player who is placing the word
     * @param gameboard a 2D array of tiles
     * @throws InvalidOrientationException if the orientation is not either vertical or horizontal
     */
    public static void placeWord(String word, String orientation, Coordinate start, Player player, Board gameboard)throws InvalidOrientationException{

        boolean hasRemoved = false;

        int row = start.getY();
        int col = start.getX();

        //Loops through all the letters in the word
        for (String letter : toStringArray(word)){
            hasRemoved = false;
            //places the word
            gameboard.getTile()[row][col].setPiece(new Piece(letter));
            //removes placed piece from player deck
            for (Iterator<Piece> it = player.getDeck().iterator(); it.hasNext();) {
                Piece p = it.next();
                if (p.getLetter().equals(letter.toUpperCase()) && hasRemoved == false) {
                    it.remove();
                    hasRemoved = true;
                }
            }

            //iterators through the tiles that the word is placed on
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
    
    /**
     * It takes a string and returns an arrayList of the characters in the string
     * 
     * @param expr the expression to be evaluated
     * @return An ArrayList of Strings
     */
    public static ArrayList<String> toStringArray(String expr){
        ArrayList<String> wordArray = new ArrayList<String>();
        //loops through all characters in a string and adds them to an arrayList
        for (int i = 0; i < expr.length(); i++){
            wordArray.add(expr.substring(i, i + 1));
        }
        return wordArray;
    } 
        

    /**
     * It takes a word, an orientation, a letter, a coordinate, and a gameboard, and returns the first
     * coordinate of the word
     * 
     * @param word the word that the user is trying to place on the board
     * @param orientation "vertical" or "horizontal"
     * @param letter the letter that the user wants to place on the board
     * @param letterCord the coordinate of the letter on the board
     * @param gameboard the board object
     * @return The starting coordinate of the word.
     */
    public static Coordinate getStartFromLetter(String word, String orientation, String letter, Coordinate letterCord, Board gameboard){
        //sets starting point for iteration
        int col = letterCord.getX();
        int row = letterCord.getY();


        //iterators backward from the letter until there is a tile which is not occupied, to find the first starting coordinate
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

    /**
     * This function takes in an orientation and returns the opposite orientation
     * 
     * @param orientation the orientation of the ship
     * @return The opposite orientation of the given orientation.
     */
    public static String oppositeOrientation(String orientation){
        //returns opposites orientation
        if (orientation.equals("vertical")){
            return "horizontal";
        }
        else{
            return "vertical";
        }
    }

    /**
     * This function outputs the player menu for selecting actions
     * 
     * @param player player object who is using the menu
     * @param gameboard a Board object
     */
    public static void playerMenu(Player player, Board gameboard){
        //outputs player menu for selecting actions
        System.out.println(gameboard.toString());
        System.out.println(player.getName() + "\'s turn!" );
        System.out.println(player.deckToString());
        System.out.println("\n1. Play\n2. Exchange\n3. Pass");
    }

    /**
     * The function takes in a player and a bag, and then asks the user to input the pieces they want
     * to exchange. It then replaces the pieces with random pieces from the bag
     * 
     * @param player a player that wants to exchange pieces
     * @param bag a bag object that contains all the pieces
     */
    public static void exchangePieces(Player player, Bag bag) {
        //Takes input for pieces that the user wants to exchanage 
        ArrayList<Integer> replacePiecesList;
        System.out.println("Enter pieces to replace: ");
        while (true){
            try{
                String replacePieces = getInput().replace(" ", "");
                //creates an integer arrayList of pieces that need to be replaced
                replacePiecesList = new ArrayList<>();      
                for (int i = 0; i < replacePieces.length(); i ++){
                    replacePiecesList.add(Integer.valueOf(replacePieces.substring(i, i + 1)));
                }
                if (replacePiecesList.size() > player.getDeck().size()){
                    throw new InvalidExchangeFormatException();
                }
                Random rand = new Random();
                //Replaces unwanted tiles with a random piece from bag
                for (int pieceindex : replacePiecesList){
                    if (pieceindex > 7 || pieceindex < 1){
                        throw new InvalidExchangeFormatException();
                    }
                    pieceindex = pieceindex - 1;
                    int randint = rand.nextInt(0, bag.getContents().size());
                    Piece randPiece = bag.getContents().get(randint);
                    player.getDeck().set(pieceindex, randPiece);
                    bag.getContents().remove(randint);
                }
                break;
            }
            catch(NumberFormatException e){
                System.out.println("Must be in format: 1 4 3 7");
            }
            catch(InvalidExchangeFormatException e){
                System.out.println("Exchange format incorrect!");
            }
        }
    }


    /**
     * It reads the last line of a text file, and returns the number after the hyphen, which is the highscore.
     * 
     * @return The highscore is being returned.
     */
    public static int getHighScore(){
        int score = 0;
        //loops through highscore file, and finds the highscore
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
        //error handling for when a file isn't found
        catch (FileNotFoundException e){
            System.out.println("File does not exist.");
        }
        return score;
    }

    /**
     * This function writes the player's name and score to a file if the player's score is higher than
     * the current high score
     * 
     * @param player the player object that has acheived the highscore
     */
    public static void writeHighScore(Player player){
        //checks that player score is higher than current highscore
        if (player.getPoints() > getHighScore()){
            //writes to the highscore file the new highscore and who acheveied it 
            try{
                File file = new File("highscore.txt");
                FileWriter writer = new FileWriter(file, true);
                writer.write("\n" + player.getName() + " - " + String.valueOf(player.getPoints()));
                writer.close();
            }
            //error handling for when a file can't be opened
            catch (IOException e) {
                System.out.println("File cannot be opened.");
            }
        }
    }

    /**
     * This function takes in a player, gameboard, bag, and boolean value and prompts the user to enter
     * a word, orientation, and starting coordinate for placing a word. If the word is valid, it places
     * the word on the board and adds the points to the player's score
     * 
     * @param player the player who is playing the word
     * @param gameboard the gameboard object
     * @param bag the bag of tiles
     * @param isFirstTurn boolean that determines if it's the first turn of the game
     */
    public static void playWord(Player player, Board gameboard, Bag bag, boolean isFirstTurn){
        while (true){
            try {
                //takes in the word, orientation of word, and starting coordinate for placing a word
                System.out.println("Enter word:");
                String word = getInput();
    
                System.out.println("Enter Orientation (vertical/horizontal):");
                String orientation = getInput();
    
                System.out.println("Enter start Coordinate: ");
                Coordinate start = Coordinate.translateString(getInput());
                
                //validates that a word is a valid and can be placed
                if (Validator.validateInput(word, orientation, start, player, gameboard, isFirstTurn)){
                    placeWord(word, orientation, start, player, gameboard);
                    System.out.printf("That play was worth %s points! \n", Integer.toString(Scorer.tallyPlay(word, start, orientation, player, gameboard, bag)));
                    player.setPoints(player.getPoints() + Scorer.tallyPlay(word, start, orientation, player, gameboard, bag));
                }
                break;
            }
            //error handling for a variety of cases
            catch(NotOnStarException e){
                System.out.println("First word must be placed on the star in the middle!");
            }
            catch(WordAlreadyUsedException e){
                System.out.println("That word has already been used!");
            }
            catch(WordNotConnectedException e){
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
            catch(InvalidCoordinateException e){
                System.out.println("Invalid Coordinate!");
            }
        }
        
    }


    /**
     * This function is called by the main game loop and is responsible for handling the player's turn
     * 
     * @param player the player whose turn it is
     * @param gameboard a 2D array of tiles
     * @param bag a bag of tiles
     * @param isFirstTurn boolean, true if it's the first turn of the game, false otherwise
     */
    public static void turn(Player player, Board gameboard, Bag bag, boolean isFirstTurn)throws IllegalArgumentException, ChoiceOutofBoundsException{
        playerMenu(player, gameboard);
        //gets the choice for the turn
        int choice;
        while (true){
            try {
                choice = Integer.valueOf(getInput());
                break;
            }
            catch(NumberFormatException e){
                System.out.println("Choice must be a number from 1-3!");
            }
        }

        //calls respective method for player choice
        switch(choice){
            case 1:
                playWord(player, gameboard, bag, isFirstTurn);
                player.setPassNum(0);
                break;
            case 2:
                exchangePieces(player, bag);
                player.setPassNum(0);
                break;
            case 3:
                player.setPassNum(player.getPassNum() + 1);
                break;
            default:
                //
                throw new ChoiceOutofBoundsException();
        }
        if (bag.getContents().size() > 0){
            // if the bag isn't empty, refiles the players hand
            player.drawDeck(gameboard, bag);
        }
    }

    /**
     * This function generates a player object and returns it
     * 
     * @param gameboad the board object
     * @param bag a bag of pieces
     * @param playerID the player's ID number
     * @return A player object
     */
    public static Player generateplayer(Board gameboad, Bag bag, int playerID){
        //output to prompt for player name
        System.out.printf("Enter player #%d name:", playerID + 1);

        String playerName = getInput();
        Player player = new Player(playerID, playerName, 0, new ArrayList<Piece>());
        player.drawDeck(gameboad, bag); 
        return player;
    }

    /**
     * It takes a list of players and a bag of pieces, then it randomly assigns a piece to each player,
     * then it finds the player with the highest piece, and then it calculates and outputs the order of the players
     * 
     * @param playerList ArrayList of player objects
     * @param bag a bag object
     * @return An ArrayList of players
     */
    public static ArrayList<Player> createPlayerOrder(ArrayList<Player> playerList, Bag bag){
        Random rand = new Random();
        ArrayList<String> alpha = toStringArray("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        ArrayList<Piece> pieceList = new ArrayList<>();
        Piece currentPiece;
        //draws a random piece from the bag for each player
        for (int i = 0; i < playerList.size(); i ++){
            currentPiece = bag.getContents().get(rand.nextInt(0, bag.getContents().size()));
            pieceList.add(currentPiece);
            System.out.printf("%s has drawn a %s!\n", playerList.get(i).getName(), currentPiece.getLetter());
        }
        //finds who has the closest piece to A
        Piece highestPiece = new Piece("Z");
        Player currentPlayer;
        for (int a = 0; a < pieceList.size(); a ++){
            if (pieceList.get(a).getLetter() == "."){
                currentPlayer = playerList.get(a).clone();
                playerList.remove(a);
                playerList.add(0, currentPlayer);
                break;
            }
            if (alpha.indexOf(pieceList.get(a).getLetter()) < alpha.indexOf(highestPiece.getLetter())){
                highestPiece = pieceList.get(a);
            }
        }
        //reorganizes player who has the piece closest to A
        currentPlayer = playerList.get(pieceList.indexOf(highestPiece)).clone();
        playerList.remove(pieceList.indexOf(highestPiece));
        playerList.add(0, currentPlayer);
        System.out.printf("%s drew the highest letter, they will be first!\n", currentPlayer.getName());
        //outputs order
        System.out.println("The order is as follows:");
        for (int i = 0; i < playerList.size();i++){
            System.out.printf("%d. %s\n", i + 1, playerList.get(i).getName());
        }
        return playerList;
    

    }

    /**
     * It takes an ArrayList of players and returns true if all players have the same number of points
     * 
     * @param playerList ArrayList of player objects
     * @return A boolean value
     */
    public static boolean isTie(ArrayList<Player> playerList){
        //finds base value to compare everything else to
        int basevalue = playerList.get(0).getPoints();
        int ties = 0;

        //detects if all other values are same
        for (int i = 1; i < playerList.size(); i++){
            if (playerList.get(i).getPoints() == basevalue){
                ties++;
            }
        }

        if (ties == playerList.size() - 1){
            return true;
        }
        else {
            return false;
        }
    }
    
    
    /**
     * This function takes in an ArrayList of players and returns the player with the highest score
     * 
     * @param playerList ArrayList of players
     * @return The player with the highest score
     */
    public static Player getWinner(ArrayList<Player> playerList){
        //creates a fake winner to compare too
        Player highestPlayer = new Player(0, "test", 0, null);
        Player currentPlayer;
        //compares all players score to each other
        for (int i = 0; i < playerList.size(); i ++){
            currentPlayer = playerList.get(i);
            if (currentPlayer.getPoints() > highestPlayer.getPoints()){
                highestPlayer = currentPlayer.clone();
                
            }
        }
        //returns player with highest score
        return highestPlayer;
    }

    
    /** 
     * Takes input for and handles errors for amount of players
     * @return amount of players to be generated
     */
    public static int getPlayerAmount(){
        int playerAmount;
        while (true){
            try{
                //getting amount of players
                System.out.println("Enter amount of players: ");
                playerAmount = Integer.valueOf(getInput());
                //checks that playerAmount is a valid amount
                if (playerAmount > 1 && playerAmount < 4 ){
                    break;
                }
                else{
                    //checks that playerAmount is a valid integer
                    System.out.println("Player amount must be a number between 2-4!");
                }
            }
            catch (NumberFormatException e){
                System.out.println("Player amount be a number between 2-4!");
            }
        }
        return playerAmount;
    }


    /**
     * runs the main loop of the game
     */
    public static void main(String[] args) {
        //creating objects, generating contents
        Board gameboard = new Board();
        Bag bag = new Bag(new HashMap<String, Integer>(), new ArrayList<Piece>());
        gameboard.generateBoard();
        bag.generateContents();
        bag.generateValues();

        int playerAmount = getPlayerAmount();

        //generating players
        ArrayList<Player> playerList = new ArrayList<>();
        for (int i = 0; i < playerAmount; i ++){
            playerList.add(generateplayer(gameboard, bag, i));
        }
        //gets whose first in the player order
        playerList = createPlayerOrder(playerList, bag);
        
        boolean isFirstTurn = true;
        //label for while loop (cuz im an academic weapon)
        mainloop:
        //main game loop
        while (true){
            for (int b = 0; b < playerList.size(); b++){
                while (true){
                    try{
                        turn(playerList.get(b), gameboard, bag, isFirstTurn);
                        break;
                    }
                    catch(ChoiceOutofBoundsException e){
                        System.out.println("Choice must be from 1-3!");
                    }
                }
                
                if (playerList.get(b).getPassNum() > 1){
                    System.out.printf("%s has passed twice so the game is over!", playerList.get(b).getName());
                    break mainloop;
                }
                if (bag.getContents().size() == 0){
                    System.out.println("The bag is empty! Game over!");
                    break mainloop;
                }
                if (gameboard.getTile()[7][7].getPiece() == null){
                    isFirstTurn = true;
                }
                else {
                    isFirstTurn = false;
                }
                
            }
        }

        //logic for win condition output
        if (! isTie(playerList)){
            Player winner = getWinner(playerList);
            System.out.printf("%s wins with %d points! Congratulations!", winner.getName(), winner.getPoints());
            writeHighScore(winner);
        }
        else {
            System.out.println("This match is a tie! No one with get a highscore because they are bad!");
        }

    }
}