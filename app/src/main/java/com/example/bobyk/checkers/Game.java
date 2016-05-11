package com.example.bobyk.checkers;

/**
 * Created by bobyk on 05/06/15.
 */
public class Game{

    private Square[][] field;
    public static int whiteCount, blackCount;

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

    public static void minusWhiteCount(){
        whiteCount--;
    }

    public static int returnWhiteCount(){
        return whiteCount;
    }

    public static int returnBlackCount(){
        return blackCount;
    }

    public int currentBtn;

    public void setCurrentBtn(int x){
        currentBtn = x;
    }

    public int getCurrentBtn(){
        return currentBtn;
    }

    public static void minusBlackCount(){
        blackCount--;
    }

    public static int checkWin(){
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

    public static void lightAllFights(){

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++){
                if (Checkers.q[i][j] == Checkers.currentPlayer) {
                    if (Checkers.king[i][j]) {
                        lightOccupiedCells_King(i, j);
                    }
                    else {
                        lightOccupiedCells_Usual(i, j);
                    }
                }
            }
        }
    }

    public static void lightOccupiedCells_Usual(int x, int y){
        if (x - 2 >= 0 && y - 2 >= 0 && Checkers.q[x-2][y-2] == 0 && Checkers.q[x-1][y-1] == 3 - Checkers.currentPlayer){
            Checkers.buttons[x-2][y-2].setBackgroundResource(R.drawable.free_brown_green_selector);
        }
        if (x - 2 >= 0 && y + 2 < 8 && Checkers.q[x-2][y+2] == 0 && Checkers.q[x-1][y+1] == 3 - Checkers.currentPlayer){
            Checkers.buttons[x-2][y+2].setBackgroundResource(R.drawable.free_brown_green_selector);
        }
        if (x + 2 < 8 && y + 2 < 8 && Checkers.q[x+2][y+2] == 0 && Checkers.q[x+1][y+1] == 3 - Checkers.currentPlayer){
            Checkers.buttons[x+2][y+2].setBackgroundResource(R.drawable.free_brown_green_selector);
        }
        if (x + 2 < 8 && y - 2 >= 0 && Checkers.q[x+2][y-2] == 0 && Checkers.q[x+1][y-1] == 3 - Checkers.currentPlayer){
            Checkers.buttons[x+2][y-2].setBackgroundResource(R.drawable.free_brown_green_selector);
        }
    }

    public static void lightOccupiedCells_King(int x, int y){



        //-----------------------------// - // - //-------------------------------------------
        int xx = x - 1;
        int yy = y - 1;
        while (xx >= 0 && yy >= 0){
            if (Checkers.q[xx][yy] == Checkers.currentPlayer) {
                break;
            }
            if (Checkers.q[xx][yy] == 3 - Checkers.currentPlayer){
                xx--;
                yy--;
                while (xx >= 0 && yy >= 0 && Checkers.q[xx][yy] == 0){
                    Checkers.buttons[xx][yy].setBackgroundResource(R.drawable.free_brown_green_selector);
                    xx--;
                    yy--;
                }
                break;
            }
            xx--;
            yy--;
        }

        //-----------------------------// - // + //-------------------------------------------

        xx = x - 1;
        yy = y + 1;
        while (xx >= 0 && yy < 8){
            if (Checkers.q[xx][yy] == Checkers.currentPlayer) {
                break;
            }
            if (Checkers.q[xx][yy] == 3 - Checkers.currentPlayer){
                xx--;
                yy++;
                while (xx >= 0 && yy < 8 && Checkers.q[xx][yy] == 0){
                    Checkers.buttons[xx][yy].setBackgroundResource(R.drawable.free_brown_green_selector);
                    xx--;
                    yy++;
                }
                break;
            }
            xx--;
            yy++;
        }

        //-----------------------------// + // + //-------------------------------------------

        xx = x + 1;
        yy = y + 1;
        while (xx < 8 && yy < 8){
            if (Checkers.q[xx][yy] == Checkers.currentPlayer) {
                break;
            }
            if (Checkers.q[xx][yy] == 3 - Checkers.currentPlayer){
                xx++;
                yy++;
                while (xx < 8 && yy < 8 && Checkers.q[xx][yy] == 0){
                    Checkers.buttons[xx][yy].setBackgroundResource(R.drawable.free_brown_green_selector);
                    xx++;
                    yy++;
                }
                break;
            }
            xx++;
            yy++;
        }

        //-----------------------------// + // - //-------------------------------------------

        xx = x + 1;
        yy = y - 1;
        while (xx < 8 && yy >= 0){
            if (Checkers.q[xx][yy] == Checkers.currentPlayer) {
                break;
            }
            if (Checkers.q[xx][yy] == 3 - Checkers.currentPlayer){
                xx++;
                yy--;
                while (xx < 8 && yy >= 0 && Checkers.q[xx][yy] == 0){
                    Checkers.buttons[xx][yy].setBackgroundResource(R.drawable.free_brown_green_selector);
                    xx++;
                    yy--;
                }
                break;
            }
            xx++;
            yy--;
        }

    }



    public static void needDo(){

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                Checkers.needDo[i][j] = false;
                Bot.fight[i][j] = false;
            }

        boolean done = false;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
            {
                if (Checkers.q[i][j] == Checkers.currentPlayer){
                    if (Checkers.king[i][j]) {
                        if (tryNeedDo_King(i, j)) {
                            done = true;
                            Checkers.needDo[i][j] = true;
                        }
                    }
                    else {
                        if (tryNeedDo_Usual(i,j)) {
                            done = true;
                            Checkers.needDo[i][j] = true;
                        }
                    }
                }
            }
        if (!done){
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    if (Checkers.q[i][j] == Checkers.currentPlayer) {
                        Checkers.needDo[i][j] = true;
                    }
                }
            }
        }
    }

    public static boolean checkOnlyNeedDo(){
        boolean ok = false;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                Checkers.needDo[i][j] = false;
                Bot.fight[i][j] = false;
            }

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
            {
                if (Checkers.q[i][j] == Checkers.currentPlayer){
                    if (Checkers.king[i][j]) {
                        if (tryNeedDo_King(i, j)) {
                            ok = true;
                            Checkers.needDo[i][j] = true;
                        }
                    }
                    else {
                        if (tryNeedDo_Usual(i,j)) {
                            ok = true;
                            Checkers.needDo[i][j] = true;
                        }
                    }
                }
            }
        return ok;
    }

    public static void onlyNeedDo(){
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                Checkers.needDo[i][j] = false;

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
            {
                if (Checkers.q[i][j] == Checkers.currentPlayer){
                    if (Checkers.king[i][j]) {
                        if (tryNeedDo_King(i, j)) {

                            Checkers.needDo[i][j] = true;
                        }
                    }
                    else {
                        if (tryNeedDo_Usual(i,j)) {
                            Checkers.needDo[i][j] = true;
                        }
                    }
                }
            }
    }

    public static boolean tryNeedDo_King(int x, int y){
        //-----------------------------// - // - //-------------------------------------------
        int xx = x - 1;
        int yy = y - 1;
        while (xx >= 0 && yy >= 0){
            if (Checkers.q[xx][yy] == Checkers.currentPlayer) {
                break;
            }
            if (Checkers.q[xx][yy] == 3 - Checkers.currentPlayer){
                int tx = xx;
                int ty = yy;
                xx--;
                yy--;
                int kol = 0;
                while (xx >= 0 && yy >= 0 && Checkers.q[xx][yy] == 0){
                    kol++;
                    xx--;
                    yy--;
                }
                if (kol > 0) {
                    Bot.fight[tx][ty] = true;
                    return true;
                }
                break;
            }
            xx--;
            yy--;
        }

        //-----------------------------// - // + //-------------------------------------------

        xx = x - 1;
        yy = y + 1;
        while (xx >= 0 && yy < 8){
            if (Checkers.q[xx][yy] == Checkers.currentPlayer) {
                break;
            }
            if (Checkers.q[xx][yy] == 3 - Checkers.currentPlayer){
                int tx = xx;
                int ty = yy;
                xx--;
                yy++;
                int kol = 0;
                while (xx >= 0 && yy < 8 && Checkers.q[xx][yy] == 0){
                    kol++;
                    xx--;
                    yy++;
                }
                if (kol > 0) {
                    Bot.fight[tx][ty] = true;
                    return true;
                }
                break;
            }
            xx--;
            yy++;
        }

        //-----------------------------// + // + //-------------------------------------------

        xx = x + 1;
        yy = y + 1;
        while (xx < 8 && yy < 8){
            if (Checkers.q[xx][yy] == Checkers.currentPlayer) {
                break;
            }
            if (Checkers.q[xx][yy] == 3 - Checkers.currentPlayer){
                int tx = xx;
                int ty = yy;
                xx++;
                yy++;
                int kol = 0;
                while (xx < 8 && yy < 8 && Checkers.q[xx][yy] == 0){
                    kol++;
                    xx++;
                    yy++;
                }
                if (kol > 0) {
                    Bot.fight[tx][ty] = true;
                    return true;
                }
                break;
            }
            xx++;
            yy++;
        }

        //-----------------------------// + // - //-------------------------------------------

        xx = x + 1;
        yy = y - 1;
        while (xx < 8 && yy >= 0){
            if (Checkers.q[xx][yy] == Checkers.currentPlayer) {
                break;
            }
            if (Checkers.q[xx][yy] == 3 - Checkers.currentPlayer){
                int tx = xx;
                int ty = yy;
                xx++;
                yy--;
                int kol = 0;
                while (xx < 8 && yy >= 0 && Checkers.q[xx][yy] == 0){
                    kol++;
                    xx++;
                    yy--;
                }
                if (kol > 0) {
                    Bot.fight[tx][ty] = true;
                    return true;
                }
                break;
            }
            xx++;
            yy--;
        }
        return false;
    }

    public static boolean tryNeedDo_Usual(int x, int y){
        if (x-2>=0 && y-2>=0 && Checkers.q[x-2][y-2] == 0 && Checkers.q[x-1][y-1] == 3-Checkers.currentPlayer){
            Bot.fight[x-1][y-1] = true;
            return true;
        }
        if (x-2>=0 && y + 2 < 8 && Checkers.q[x-2][y+2] == 0 && Checkers.q[x-1][y+1] == 3-Checkers.currentPlayer){
            Bot.fight[x-1][y+1] = true;
            return true;
        }
        if (x+2 < 8 && y - 2 >=0 && Checkers.q[x+2][y-2] == 0 && Checkers.q[x+1][y-1] == 3-Checkers.currentPlayer){
            Bot.fight[x+1][y-1] = true;
            return true;
        }
        if (x+2 < 8 && y + 2 < 8 && Checkers.q[x+2][y+2] == 0 && Checkers.q[x+1][y+1] == 3-Checkers.currentPlayer){
            Bot.fight[x+1][y+1] = true;
            return true;
        }
        return false;
    }

    public static void cleanLights(){
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++){
                if (Checkers.q[i][j] == 1) {

                    if (Checkers.king[i][j]) {
                        Checkers.buttons[i][j].setBackgroundResource(R.drawable.white_king);
                    }
                    else {
                        Checkers.buttons[i][j].setBackgroundResource(R.drawable.white_brown);
                    }

                }
                else if (Checkers.q[i][j] == 2){

                    if (Checkers.king[i][j]) {
                        Checkers.buttons[i][j].setBackgroundResource(R.drawable.black_king);
                    }
                    else {
                        Checkers.buttons[i][j].setBackgroundResource(R.drawable.black_brown);
                    }

                }
                else {
                    if ((i+j)%2!=0)Checkers.buttons[i][j].setBackgroundResource(R.drawable.brown);
                }
            }
        }
    }

}
