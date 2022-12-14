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
                else if(halfs[y][x] == 5){
                    gameBoard[y][x].setType("STAR");
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

        // for (int a = 14; a >= 8; a--){
        //     for (int b = 14; b >= 0; b--){
        //         System.out.println(a + " " + b);
        //         if (halfs[15 - a][b] == 1){
        //             System.out.println(a + " " + b);
        //             gameBoard[a + 1][b].setType("2L");
        //         }
        //         else if (halfs[15 - a][b] == 2){
                    
        //             gameBoard[a][b].setType("2W");
        //         }
        //         else if (halfs[15 - a][b] == 3){
        //             gameBoard[a][b].setType("3L");
        //         }
        //         else if (halfs[15 - a][b] == 4){
        //             gameBoard[a][b].setType("3W");
        //         }
        //         else if (halfs[15 - a][b] == 5){
        //             gameBoard[a][b].setType("STAR");
        //         }
        //         else{
                    
        //         }
        //     }
        // }
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

        
        accum += "   " + row_accum + "|" + "\n";
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
                
                cell = String.format(" |%s ", gameBoard[r][c].toString());
                row_accum += cell;
                if (c == 14){
                    row_accum += " |";
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
    }
}
