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
        
    }

    public Tile[][] getTile(){
        return gameBoard;
    }

    public String toString(){

    }
}
