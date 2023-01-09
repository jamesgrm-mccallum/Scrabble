/**
 * Class that is responsible for scoring words and plays in scrabble
 * 
 */
public class Scorer {


    /**
     * This function takes in a word, the orientation of the word, the starting coordinate of the word,
     * the gameboard, the player, and the bag. It then returns the score of the word
     * 
     * @param orientation the orientation of the word
     * @param word the word that is being played
     * @param start the starting coordinate of the word
     * @param gameboard the gameboard object
     * @param player the player who is playing the word
     * @param bag the bag of tiles
     * @return The score of the word being played.
     */
    public static int tallyWord(String orientation, String word, Coordinate start, Board gameboard, player player, Bag bag){
        int word_score = 0;
        int lettermultiplier = 1;
        int wordmultiplier = 1;

        int row = start.getY();
        int col = start.getX();

        for (String letter : Scrabble.toStringArray(word)){
            Tile currentTile = gameboard.getTile()[row][col];
            if (currentTile.getType() != null){
                //applies multiplyers for when a word is on a special tile
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
            //gets values of each letter in the word from bag
            word_score += bag.getValues().get(letter.toUpperCase()) * lettermultiplier;
            lettermultiplier = 1;

            if (orientation.equals(("vertical"))){
                row ++;
            }
            else if (orientation.equals("horizontal")){
                col++;
            }
        }
        //adds 50 points if the user uses all 7 pieces
        if (word.length() == 7){
            word_score += 50;
        }
        
        return word_score * wordmultiplier;
    }

    /**
     * This function takes a word, a starting coordinate, an orientation, a player, a gameboard, and a
     * bag and returns the score of the word being played and every connecting word that is being altered
     * 
     * @param word the word being played
     * @param start the starting coordinate of the word
     * @param orientation the orientation of the word being played
     * @param player the player who is playing the word
     * @param gameboard the board object
     * @param bag the bag of tiles
     * @return The score of the word being played.
     */
    public static int tallyPlay(String word, Coordinate start, String orientation, player player, Board gameboard, Bag bag){
        int playScore = 0;

        int row = start.getY();
        int col = start.getX();

        for (String letter : Scrabble.toStringArray(word)){
            Coordinate letterCord = new Coordinate(col, row);
            String[] connections;
            
            //finds and tallys all the connections for each letter in a vertical word
            if (orientation.equals("vertical")){
                if (row - start.getY() == word.length()){
                    connections = Validator.findConnections(letter, Scrabble.getStartFromLetter(word, Scrabble.oppositeOrientation(orientation), letter, letterCord, gameboard), "last", player, gameboard);
                }
                else{
                    connections = Validator.findConnections(letter, Scrabble.getStartFromLetter(word, Scrabble.oppositeOrientation(orientation), letter, letterCord, gameboard), orientation, player, gameboard);
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
            //finds and tallys all the connections for each letter in a vertical word
            else if(orientation.equals("horizontal")){
                if (col - word.length() == start.getX() - 1){
                    connections = Validator.findConnections(letter, Scrabble.getStartFromLetter(word, Scrabble.oppositeOrientation(orientation), letter, letterCord, gameboard), "last", player, gameboard);
                }
                else {
                    connections = Validator.findConnections(letter, Scrabble.getStartFromLetter(word, Scrabble.oppositeOrientation(orientation), letter, letterCord, gameboard), orientation, player, gameboard);
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
}
