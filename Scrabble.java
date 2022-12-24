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

    private static String getInput(){
        
            //Variable for storing input
            String userInput = input.next();
            
            //returns input
            return userInput;
    }


    public static String[] findConnections(String letter, Coordinate letterCord, player player, Board gameboard){
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
        if (gameboard.getTile()[letterCord.getY() + 1][letterCord.getX()].getPiece() != null){
            int rightCord = 1;
            while(gameboard.getTile()[letterCord.getY() + rightCord][letterCord.getX()].getPiece() != null && letterCord.getY() + rightCord <= 14){
                right += gameboard.getTile()[letterCord.getY() + rightCord][letterCord.getX()].getPiece().getLetter();
                rightCord ++;
            }
        }
        //getting the letters above the letter
        if (gameboard.getTile()[letterCord.getY()][letterCord.getX() - 1].getPiece() != null){
            int decreaseCord = 1;
            while (gameboard.getTile()[letterCord.getY()][letterCord.getX() - decreaseCord].getPiece() != null && letterCord.getX() - decreaseCord >= 0){
                up += gameboard.getTile()[letterCord.getY()][letterCord.getX() - decreaseCord].getPiece().getLetter();
                decreaseCord ++;
            }
        }
        //getting the letters below the letter
        if (gameboard.getTile()[letterCord.getY()][letterCord.getX() + 1].getPiece() != null){
            int increaseCord = 1;
            while (gameboard.getTile()[letterCord.getY()][letterCord.getX() + increaseCord].getPiece() != null && letterCord.getX() + increaseCord <= 14){
                down += gameboard.getTile()[letterCord.getY()][letterCord.getX() + increaseCord].getPiece().getLetter();
                increaseCord ++;
            }
        }
        
        //Compiling words and checking if they are in dictionary

        if (up.length() > 0 || down.length() > 0){
            wordVertical = up + letter + down;
        }
        if (left.length() > 0 || right.length() > 0){
            wordHorizontal = left + letter + right;
        }
        

        return new String[]{wordVertical, wordHorizontal};
    }


    public static boolean validateConnection(String letter, Coordinate letterCord, player player, Board gameboard){
        String[] connections = findConnections(letter, letterCord, player, gameboard);
        int trues = 0;

        if (connections[0].length() > 0){
            if (isWord(connections[0])){
                trues ++;
            }
        }
        else {
            trues++;
        }

        if (connections[1].length() > 0){
            if (isWord(connections[1])){
                trues ++;
            }
        }
        else {
            trues++;
        }
        
        if (trues == 2){
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean letterInDeck(String word, String letter, String orientation, Coordinate start, player player, Board gameboard){
            int iterations = 0;
            int row = start.getY();
            int col = start.getX();
            String accum = "";

            while (iterations < word.length()){
                String current_letter = gameboard.getTile()[row][col].getPiece().getLetter();
                if (current_letter == null){
                    accum += " ";
                }
                else {
                    accum += current_letter;
                }

                if (orientation.equals("vertical")){
                    row++;
                }
                else {
                    col++;
                }
                iterations ++;
            }
    }

    //validate word returns boolean
    //takes in: String word, String orientation, Coordinate starting pos, String player
    //checks to see if a word is a real word, player has correct pieces for word, word can fit on board

    public static boolean validateInput(String word, String direction, Coordinate start, player player, Board gameboard)throws IllegalArgumentException, WordNotWithinRangeException, WordNotFoundException, InvalidOrientationException, WordNotInDeckException, InvalidConnectionException{
        // checks that word is a word in the dictionary
        if (isWord(word)){
            // logic for getting sorted strings of player deck and word
            ArrayList<String> wordList = new ArrayList<String>(Arrays.asList(word.split("")));
            ArrayList<String> deckList = new ArrayList<String>();
            for (Piece p: player.getDeck()){
                deckList.add(p.getLetter());
            }
        
            String deckString = String.join("", deckList);
            int correct = 0;

            

            

            ArrayList<String> wordList2 = new ArrayList<String>(Arrays.asList(word.split("")));
            ArrayList<String> accumList = new ArrayList<String>(Arrays.asList(accum.split("")));

            for (int i = 0; i < wordList2.size(); i++){
                for (int x = 0; x < deckList.size(); x ++){
                    if (wordList.get(i).equals(accumList.get(i))){
                        correct ++;
                    }
                    else if (wordList2.get(i).equalsIgnoreCase(deckList.get(x))){
                        deckList.remove(x);
                        
                        correct ++;
                    }
                    
                    if (! deckString.contains(wordList2.get(i)) && accum.contains(wordList2.get(i))){
                        if (deckList.contains(".")){
                            deckList.remove(deckList.indexOf("."));
                            correct++;
                        }
                    }
                }
            }

            

            //Checks that the word has the correct pieces to be placed
            if (correct == word.length()){
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
                        throw new InvalidOrientationException();
                    }

                    //Checks that end point is within board
                    if (endPoint.getX() <= 14 && endPoint.getX() >= 0 && endPoint.getY() <= 14 && endPoint.getY() >= 0){
                        if (direction.equals("vertical")){
                            int i = start.getY();
                            for (String letter : wordList){
                                if (!validateConnection(letter, new Coordinate(start.getX(), i + 1 ), player, gameboard)){
                                    throw new InvalidConnectionException();
                                }
                            }
                            i++;
                        }
                        else {
                            int i = start.getX();
                            for (String letter : wordList){
                                if (!validateConnection(letter, new Coordinate(i + 1, start.getY()), player, gameboard)){
                                    throw new InvalidConnectionException();
                                }
                            }
                            i++;
                        }
                        return true;
                    }
                    else{
                        throw new WordNotWithinRangeException();
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
    public static int  tallyWord(String orientation, String word, Coordinate start, Board gameboard, player player, Bag bag){
        int word_score = 0;
        int lettermultiplier = 1;
        int mulitplier = 1;

        if (orientation.equals("vertical")){
            int i = start.getY();
            for (String letter : toStringArray(word)){
                Tile currentTile = gameboard.getTile()[i][start.getX()];
                if (currentTile.getType() != null){
                    if (currentTile.getType() == "2L"){
                        lettermultiplier = 2;
                    }
                    else if (currentTile.getType() == "2W"){
                        mulitplier = mulitplier * 2;
                    }
                    else if(currentTile.getType() == "3L"){
                        lettermultiplier = 3;
                    }
                    else {
                        mulitplier = mulitplier * 3;
                    }
                }
                word_score += bag.getValues().get(letter.toUpperCase()) * lettermultiplier;
                lettermultiplier = 1;
                i ++;
            }
        }
        else if(orientation.equals("horizontal")){
            int i = start.getX();
            for (String letter : toStringArray(word)){
                Tile currentTile = gameboard.getTile()[start.getY()][i];
                if (currentTile.getType() != null){
                    if (currentTile.getType() == "2L"){
                        lettermultiplier = 2;
                    }
                    else if (currentTile.getType() == "2W"){
                        mulitplier = mulitplier * 2;
                    }
                    else if(currentTile.getType() == "3L"){
                        lettermultiplier = 3;
                    }
                    else {
                        mulitplier = mulitplier * 3;
                    }
                }
                word_score += bag.getValues().get(letter.toUpperCase()) * lettermultiplier;
                lettermultiplier = 1;
                i++;
            }
        }
        else {
            throw new IllegalArgumentException();
        }

        if (word.length() == 7){
            word_score += 50;
        }
        
        return word_score * mulitplier;
    }

    public Coordinate getStartFromLetter(String word, String orientation, String letter, Coordinate letterCord, Board gameboard){
        ArrayList<String> wordAsList = toStringArray(word);
        int letterIndex = wordAsList.indexOf(letter);

        if (orientation.equals("vertical")){
            return new Coordinate(letterCord.getX(), letterCord.getY() - letterIndex);
        }
        else if (orientation.equals("horizontal")){
            return new Coordinate(letterCord.getX() - letterIndex, letterCord.getY());
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    //tallyPlay
    public int tallyPlay(String word, Coordinate start, String orientation, player player, Board gameboard, Bag bag){
        int playScore = 0;
        if (orientation.equals("vertical")){
            int i = start.getY();
            for (String letter : toStringArray(word)){
                Coordinate letterCord = new Coordinate(start.getX(), start.getY() + (i - start.getY()));
                String[] connections = findConnections(letter , getStartFromLetter(word, orientation, letter, letterCord, gameboard), player, gameboard);
                playScore += tallyWord(orientation, connections[0], start, gameboard, player, bag);
                i++;
            }
        }
        else if (orientation.equals("horizontal")){
            int i = start.getX();
            for (String letter : toStringArray(word)){
                Coordinate letterCord = new Coordinate(start.getX() + (i - start.getX()), start.getY());
                String[] connections = findConnections(letter , getStartFromLetter(word, orientation, letter, letterCord, gameboard), player, gameboard);
                playScore += tallyWord(orientation, connections[1], start, gameboard, player, bag);
                i++;
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
    


    public static void playWord(player player, Board gameboard, Bag bag){
        try {
            System.out.println("Enter word:");
            String word = getInput();

            System.out.println("Enter Orientation (vertical/horizontal):");
            String orientation = getInput();

            System.out.println("Enter start Coordinate: ");
            Coordinate start = Coordinate.translateString(getInput());
            System.out.println(start);
            
            if (validateInput(word, orientation, start, player, gameboard)){
                placeWord(word, orientation, start, player, gameboard);
                tallyWord(orientation, word, start, gameboard, player, bag);
            }
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
    }


    public static void turn(player player, Board gameboard, Bag bag)throws IllegalArgumentException{
        playerMenu(player, gameboard);
        System.out.println(">>> ");
        int choice = Integer.valueOf(getInput());
        switch(choice){
            case 1:
                playWord(player, gameboard, bag);
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
                throw new IllegalArgumentException(); //FIXME add ChoiceOutofBoundsException
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
        System.out.println(bag.getValues());
        player1.drawDeck(gameboard, bag);
        player2.drawDeck(gameboard, bag);

        boolean isPlayerOneTurn = true;
        while (player1.getPassNum() < 2 && player2.getPassNum() < 2){
            if (isPlayerOneTurn){
                turn(player1, gameboard, bag);
                isPlayerOneTurn = false;
            }
            else {
                turn(player2, gameboard, bag);
                isPlayerOneTurn = true;
            }
        }

    }
}