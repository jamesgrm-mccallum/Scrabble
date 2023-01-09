public class Scorer {


    public static int tallyWord(String orientation, String word, Coordinate start, Board gameboard, player player, Bag bag){
        int word_score = 0;
        int lettermultiplier = 1;
        int wordmultiplier = 1;

        int row = start.getY();
        int col = start.getX();

        for (String letter : Scrabble.toStringArray(word)){
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

    public static int tallyPlay(String word, Coordinate start, String orientation, player player, Board gameboard, Bag bag){
        int playScore = 0;

        int row = start.getY();
        int col = start.getX();

        for (String letter : Scrabble.toStringArray(word)){
            Coordinate letterCord = new Coordinate(col, row);
            String[] connections;
            
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
