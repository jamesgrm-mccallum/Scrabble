class Board {
    private Tile[][] gameBoard;

    private Board(){
        gameBoard = new Tile[15][15];
    }

    private void generateBoard(){
        for (int x = 0; x < 14; x ++){
            for (int y = 0; y < 14; y ++){
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
        int[] middle =   {4, 0, 0, 1, 0, 0, 0, 5, 0, 0, 0, 1, 0, 0, 3};

        for (int y = 0; y < 6; y++){
            for (int x = 0; x < 14; x ++){
                if (halfs[x][y] == 1){
                    gameBoard[x + 1][y + 1].setType("2L");
                }
                else if(halfs[x][y] == 2){
                    gameBoard[x + 1][y + 1].setType("2W");
                }
                else if(halfs[x][y] == 3){
                    gameBoard[x + 1][y + 1].setType("3L");
                }
                else if(halfs[x][y] == 4){
                    gameBoard[x + 1][y + 1].setType("3W");
                }
                else if(halfs[x][y] == 5){
                    gameBoard[x + 1][y + 1].setType("STAR");
                }
                else{
                    
                }
            }
        }

        for (int a = 6; a > 0; a--){
            for (int b = 14; b > 0; b--){
                if (halfs[b][a] == 1){
                    gameBoard[b + 1][a + 9].setType("2L");
                }
                else if (halfs[b][a] == 2){
                    gameBoard[b + 1][a + 9].setType("2W");
                }
                else if (halfs[b][a] == 3){
                    gameBoard[b + 1][a + 9].setType("3L");
                }
                else if (halfs[b][a] == 4){
                    gameBoard[b + 1][a + 9].setType("3W");
                }
                else if (halfs[b][a] == 5){
                    gameBoard[b + 1][a + 9].setType("STAR");
                }
                else{
                    
                }
            }
        }
        ////Star
        // gameBoard[8][8].setType("star");
        
        ////Special Tiles for left side of board
        // gameBoard[1][1].setType("triple word");
        // gameBoard[1][4].setType("double letter"); 
        // gameBoard[1][8].setType("triple word");
        // gameBoard[1][12].setType("double letter");
        // gameBoard[1][15].setType("triple word");
        // gameBoard[2][2].setType("double word");
        // gameBoard[2][6].setType("triple letter");
        // gameBoard[2][10].setType("triple letter");
        // gameBoard[2][14].setType("double word");
        // gameBoard[3][3].setType("double word");
        // gameBoard[3][7].setType("double letter");
        // gameBoard[3][9].setType("double letter");
        // gameBoard[3][13].setType("double word");
        // gameBoard[4][1].setType("double letter");
        // gameBoard[4][4].setType("double word");
        // gameBoard[4][8].setType("double letter");
        // gameBoard[4][12].setType("double word");
        // gameBoard[4][15].setType("double letter");
        // gameBoard[5][5].setType("double word");
        // gameBoard[5][11].setType("double word");
        // gameBoard[6][2].setType("triple letter");
        // gameBoard[6][6].setType("triple letter");
        // gameBoard[6][10].setType("triple letter");
        // gameBoard[6][14].setType("triple letter");
        // gameBoard[7][8].setType("double letter");
        // gameBoard[7][8].setType("double letter");
        // gameBoard[7][8].setType("double letter");
        // gameBoard[7][8].setType("double letter");







    }

    public Tile[][] getTile(){
        return gameBoard;
    }

    public String toString(){
        String row_accum = "";
        String cell;
        for (int r = 0; r < 14; r++){
            for (int c = 0; c < 14; c++){
                cell = String.format("|%s|", gameBoard[c][r].toString());
                row_accum += cell;
            }
        }
        return row_accum;
    }
    public static void main(String[] args) {
        gameBoard.toString();
    }
}
