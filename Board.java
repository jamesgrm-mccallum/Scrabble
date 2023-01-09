// import java.util.Scanner;

class Board {
    private Tile[][] gameBoard;

    /**
     * Constructor for the board object, always creates a 15x15 board
     */
    public Board(){
        // [ROW][COL]
        gameBoard = new Tile[15][15];
    }

    /**
     * this functions generates the special tiles that a scrabble board has
     */
    public void generateBoard(){
        for (int x = 0; x < 15; x ++){
            for (int y = 0; y < 15; y ++){
                gameBoard[x][y] = new Tile(new Coordinate(x, y), null, null);
            }
        }

        // key
        // 0 = normal
        // 1 = double letter
        // 2 = double word
        // 3 = triple letter
        // 4 = triple word 
        
        //top half of the board
        int[][] halfs = {{4, 0, 0, 1, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 4}, 
                         {0, 2, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 2, 0},
                         {0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 0, 2, 0, 0},
                         {1, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 1},
                         {0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0},
                         {0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0},
                         {0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0}};
        //middle row of the board
        int[] middle =   {4, 0, 0, 1, 0, 0, 0, 5, 0, 0, 0, 1, 0, 0, 4};
        //loops through board and and creates files
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
            //loops reverse through top half to create bottom half
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
        //looping through middle
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
        }
    }

    /**
     * This function returns a tile on the board
     * 
     * @return tile on the gameboard.
     */
    public Tile[][] getTile(){
        return gameBoard;
    }


    
    /**
     * This function creates a string representation of the game board, which is a 15x15 grid of cells
     * 
     * @return A string representation of the game board.
     */
    @Override
    public String toString(){
        String accum = "";
        String row_accum = "";
        String cell = "";
        //assembling cells for top alphabet coordinate index
        for (int h = 0; h < 15; h++){
            char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
            cell = ""; 
            cell += String.format("| %s ", alphabet[h]);
            row_accum += cell;
        
        }

        // buffering for ends of rows
        accum += "  " + row_accum + "|" + "\n";
        //creating each row and adding to final accum
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
    
}
