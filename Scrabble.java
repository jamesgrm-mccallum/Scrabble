import java.util.ArrayList;
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
    

    public static void playerMenu(player player, Board gameboard){
        System.out.println(gameboard.toString());
        System.out.println(player.getName() + "\'s turn!" );
        System.out.println(player.deckToString());
        System.out.println("\n1. Play\n2. Exchange\n3. Pass");
    }

    public static void exchangetiles(player player, Bag bag) {
        System.out.println("Enter pieces to replace: ");
        String replacePieces = getInput().replace(" ", "");

        ArrayList<Integer> replacePiecesList = new ArrayList<>();      
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

    public static void writeHighScore(player player){
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
                    System.out.printf("That play was worth %s points!", Integer.toString(Scorer.tallyPlay(word, start, orientation, player, gameboard, bag)));
                    player.setPoints(player.getPoints() + Scorer.tallyPlay(word, start, orientation, player, gameboard, bag));
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

    public static player generateplayer(Board gameboad, Bag bag, int playerID){
        System.out.printf("Enter player #%d name:", playerID + 1);
        String playerName = getInput();
        player player = new player(playerID, playerName, 0, new ArrayList<Piece>());
        player.drawDeck(gameboad, bag); 
        return player;
    }

    public static ArrayList<player> createPlayerOrder(ArrayList<player> playerList, Bag bag){
        Random rand = new Random();
        ArrayList<String> alpha = toStringArray("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        ArrayList<Piece> pieceList = new ArrayList<>();
        Piece currentPiece;
        for (int i = 0; i < playerList.size(); i ++){
            currentPiece = bag.getContents().get(rand.nextInt(0, bag.getContents().size()));
            pieceList.add(currentPiece);
            System.out.printf("%s has drawn a %s!\n", playerList.get(i).getName(), currentPiece.getLetter());
        }
        Piece highestPiece = new Piece("Z");
        player currentPlayer;
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
        currentPlayer = playerList.get(pieceList.indexOf(highestPiece)).clone();
        playerList.remove(pieceList.indexOf(highestPiece));
        playerList.add(0, currentPlayer);
        System.out.printf("%s drew the highest letter, they will be first!\n", currentPlayer.getName());
        System.out.println("The order is as follows:");
        for (int i = 0; i < playerList.size();i++){

        }


        return playerList;

    }

    public static boolean isTie(ArrayList<player> playerList){
        int basevalue = playerList.get(0).getPoints();
        int ties = 0;

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
    
    public static player getWinner(ArrayList<player> playerList){
        player highestPlayer = new player(0, "test", 0, null);
        player currentPlayer;
        for (int i = 0; i < playerList.size(); i ++){
            currentPlayer = playerList.get(i);
            if (currentPlayer.getPoints() > highestPlayer.getPoints()){
                highestPlayer = currentPlayer.clone();
                
            }
        }
        return highestPlayer;
    }


    public static void main(String[] args) {
        //creating objects
        Board gameboard = new Board();
        Bag bag = new Bag(new HashMap<String, Integer>(), new ArrayList<Piece>());
        gameboard.generateBoard();
        bag.generateContents();
        bag.generateValues();

        System.out.println("Enter amount of players: ");
        int playerAmount = Integer.valueOf(getInput());

        ArrayList<player> playerList = new ArrayList<>();
        for (int i = 0; i < playerAmount; i ++){
            playerList.add(generateplayer(gameboard, bag, i));
        }
        playerList = createPlayerOrder(playerList, bag);
        
        boolean isFirstTurn = true;
        outerloop:
        while (true){
            for (int b = 0; b < playerList.size(); b++){
                turn(playerList.get(b), gameboard, bag, isFirstTurn);
                if (playerList.get(b).getPassNum() > 1){
                    System.out.printf("%s has passed twice so the game is over!", playerList.get(b).getName());
                    break outerloop;
                }
                if (bag.getContents().size() == 0){
                    System.out.println("The bag is empty! Game over!");
                    break outerloop;
                }
                isFirstTurn = false;
            }
        }

        if (! isTie(playerList)){
            player winner = getWinner(playerList);
            System.out.printf("%s wins with %d points! Congratulations!", winner.getName(), winner.getPoints());
            writeHighScore(winner);
        }
        else {
            System.out.println("This match is a tie! No one with get a highscore because they are bad!");
        }

    }
}