class Board {
    private Tile[][] gameBoard;

    private Board(){
        gameBoard = new Tile[15][15];
    }

    private void generateBoard(){
        for (int x = 0; x < 15; x ++){
            for (int y = 0; y < 15; y ++){
                gameBoard[x][y] = new Tile(new Coordinate(x, y), null, null);
            }
        }
        //Star
        gameBoard[8][8].setType("star");
        
        //Special Tiles for left side of board
        gameBoard[1][1].setType("Triple Word");
        gameBoard[1][4].setType("Double Letter"); 
        

    }

    public Tile[][] getTile(){
        return gameBoard;
    }

    public String toString(){

    }
}
