package com.example.bobyk.checkers;

/**
 * Created by bobyk on 05/06/15.
 */
public class Game{

    private Square[][] field;
    private int whiteCount, blackCount;

    private Player activePlayer;
    private Player[] players;
    private boolean started;

    public Game() {
        field = new Square[8][8];
        whiteCount = 12;
        blackCount = 12;
        for (int i = 0, l1 = field.length; i < l1; i++){
            for (int j = 0, l2 = field.length; j < l2; j++){
                field[i][j] = new Square();
                if ((i+j) % 2 != 0 && i < 3) {
                    field[i][j].fill(new Player (2));
                }
                else if ((i+j) % 2 != 0 && i > 4) {
                    field[i][j].fill(new Player (1));
                }
            }
        }
        started = false;
        players = new Player[2];
        activePlayer = null;
    }

    public Square[][] getField(){
        return field;
    }

    public void start(){
        resetPlayers();
        started = true;
    }

    public void resetPlayers(){
        players[0] = new Player (1);
        players[1] = new Player (2);
        setActivePlayer(players[0]);
    }

    public void minusWhiteCount(){
        whiteCount--;
    }

    public int returnWhiteCount(){
        return whiteCount;
    }

    public int returnBlackCount(){
        return blackCount;
    }

    public int currentBtn;

    public void setCurrentBtn(int x){
        currentBtn = x;
    }

    public int getCurrentBtn(){
        return currentBtn;
    }

    public void minusBlackCount(){
        blackCount--;
    }

    public int checkWin(){
        if (whiteCount == 0)
        {
            return 2;
        }
        else if (blackCount == 0)
        {
            return 1;
        }
        else return -1;
    }

    public void isKing(int x, int y){
        field[x][y].isKing();
    }

    public void isNotKing(int x, int y){
        field[x][y].isNotKing();
    }

    public boolean getKing(int x, int y){
        return field[x][y].getKing();
    }

    public void setActivePlayer(Player player){
        activePlayer = player;
    }

    public Player getActivePlayer(){
        return activePlayer;
    }

}
