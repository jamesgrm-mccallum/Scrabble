// import java.util.Scanner;
import java.util.ArrayList;

class Board {
    private Tile[][] gameBoard;

    private Board(){
        // [ROW][COL]
        gameBoard = new Tile[15][15];
    }

    public void generateBoard(){
        for (int x = 0; x < 15; x ++){
            for (int y = 0; y < 15; y ++){
                gameBoard[x][y] = new Tile(new Coordinate(x, y), null, null);
            }
        }
                                            // 0 = normal
                                            // 1 = double letter
                                            // 2 = double word
                                            // 3 = triple letter
                                            // 4 = triple word 

        int[][] halfs = {{4, 0, 0, 1, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 4}, 
                         {0, 2, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 2, 0},
                         {0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 0, 2, 0, 0},
                         {1, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 1},
                         {0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0},
                         {0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0},
                         {0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0}};
        int[] middle =   {4, 0, 0, 1, 0, 0, 0, 5, 0, 0, 0, 1, 0, 0, 4};

        for (int y = 0; y < 7; y++){
            for (int x = 0; x < 15; x ++){
                if (halfs[y][x] == 1){
                    gameBoard[y][x].setType("2L");
                }
                else if(halfs[y][x] == 2){
                    gameBoard[y][x].setType("2W");
                }
                else if(halfs[y][x] == 3){
                    gameBoard[y][x].setType("3L");
                }
                else if(halfs[y][x] == 4){
                    gameBoard[y][x].setType("3W");
                }
                else{
                    
                }
            }
            for (int x = 0; x < 15; x ++){
                if (halfs[y][x] == 1){
                    gameBoard[14 - y][14 - x].setType("2L");
                }
                else if(halfs[y][x] == 2){
                    gameBoard[14 - y][14 - x].setType("2W");
                }
                else if(halfs[y][x] == 3){
                    gameBoard[14 - y][14 - x].setType("3L");
                }
                else if(halfs[y][x] == 4){
                    gameBoard[14 - y][14 - x].setType("3W");
                }
                else{
                    
                }
            }
        }

        for (int i = 0; i < 15; i++ ){
            if (middle[i] == 1){
                gameBoard[7][i].setType("2L");
            }
            else if (middle[i] == 2){
                gameBoard[7][i].setType("2W");
            }
            else if (middle[i] == 3){
                gameBoard[7][i].setType("3L");
            }
            else if (middle[i] == 4){
                gameBoard[7][i].setType("3W");
            }
            else if (middle[i] == 5){
                gameBoard[7][i].setType("STAR");
            }
            else{
                
            }
        }
    }

    public Tile[][] getTile(){
        return gameBoard;
    }

    @Override
    public String toString(){
        String accum = "";
        String row_accum = "";
        String cell = "";
        for (int h = 0; h < 15; h++){
            char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
            cell = ""; 
            cell += String.format("| %s ", alphabet[h]);
            row_accum += cell;
        
        }

        
        accum += "  " + row_accum + "|" + "\n";
        for (int r = 0; r < 15; r++){
            row_accum = "";
            row_accum += "---------------------------------------------------------------\n";
            for (int c = 0; c < 15; c++){
                if (c == 0){
                    if (r < 9){
                        row_accum += " ";
                    }
                    row_accum += String.valueOf(r + 1);
                }
                
                cell = String.format("| %s ", gameBoard[r][c].toString());
                row_accum += cell;
                if (c == 14){
                    row_accum += "|";
                }
                
            }
            row_accum += "\n";
            accum += row_accum;
        }
        
        return accum;
    }
    public static void main(String[] args) {
        Board gameBoard = new Board();
        gameBoard.generateBoard();
        System.out.println(gameBoard);
        Coordinate start = new Coordinate(3, 3);
        ArrayList<Piece> letters = new ArrayList<Piece>();
        letters.add(new Piece("H"));
        letters.add(new Piece("E"));
        letters.add(new Piece("L"));
        letters.add(new Piece("L"));
        letters.add(new Piece("O"));
        letters.add(new Piece("O"));
        
        player player1 = new player(0, "James", 0, letters);
        Scrabble.placeWord("hello", "vertical", start, player1, gameBoard);
        System.out.println(gameBoard);
        System.out.println(player1.getDeck());
    }
}
