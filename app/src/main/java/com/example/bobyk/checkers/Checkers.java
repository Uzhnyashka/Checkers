package com.example.bobyk.checkers;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by bobyk
 * **/

 public class Checkers extends Activity {

    private Game game;
    public static Button[][] buttons = new Button[8][8];
    private TableLayout layout;

    public static int kolClick;
    public static int currentX, currentY;
    public static int currentPlayer;
    public static int[][] q = new int[8][8];
    public static boolean[][] king = new boolean[8][8];
    public static boolean[][] avail = new boolean[8][8];
    public boolean onePlayerGame1;
    private boolean onePlayerGame;
    public static boolean[][] needDo = new boolean[8][8];
    public int fight = 1;
    public static boolean[][] onlyNeedDo = new boolean[8][8];

    public static TextView txtView1, txtView2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (MenuActivity.REQUEST_CODE == 1) onePlayerGame1 = true;
        else onePlayerGame1 = false;

        setContentView(R.layout.activity_main);

        layout = (TableLayout) findViewById(R.id.main_l);
        txtView1 = (TextView) findViewById(R.id.textView);
        txtView2 = (TextView) findViewById(R.id.textView2);

        buildGameField();
    }

    private void buildGameField() {
        onePlayerGame = onePlayerGame1;
        game = new Game();
        txtView1.setText("White: " + Integer.toString(Game.returnWhiteCount()));
        txtView2.setText("Black: " + Integer.toString(Game.returnBlackCount()));
        Square[][] field = game.getField();
        for (int i = 0, lenI = field.length; i < lenI; i++ ) {
            TableRow row = new TableRow(this);
            for (int j = 0, lenJ = field[i].length; j < lenJ; j++) {
                Button button = new Button(this);
                buttons[i][j] = button;
                button.setOnClickListener(new Listener(i, j));
                row.addView(button, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                Display display = getWindowManager().getDefaultDisplay();

                button.setWidth((display.getWidth()-50)/8);
                button.setHeight((display.getHeight()-50)/13);

                if ((i+j)%2!=0 && i < 3) {
                    q[i][j] = 2;
                    button.setBackgroundResource(R.drawable.black_brown);
                }
                else if ((i+j)%2!=0 && i > 4) {
                    q[i][j] = 1;
                    button.setBackgroundResource(R.drawable.white_brown);
                }
                else if ((i+j)%2 == 0){
                    q[i][j] = 0;
                    button.setBackgroundResource(R.drawable.whity);
                }
                else {
                    q[i][j] = 0;
                    button.setBackgroundResource(R.drawable.brown);
                }
            }
            layout.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
        currentPlayer = 1;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                king[i][j] = false;
            }
        }
        availFalse();
        Game.needDo();
    }

    public static void availFalse(){
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                avail[i][j] = false;
            }
        }
    }

    public class Listener implements View.OnClickListener {
        private int x = 0;
        private int y = 0;

        public Listener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void onClick(View view) {
            if (!(MenuActivity.REQUEST_CODE == 1 && currentPlayer == 2)) {
                Game.needDo();
                Game.lightAllFights();
                kolClick++;
                if (kolClick % 2 == 1) {
                    currentX = x;
                    currentY = y;
                    Game.cleanLights();
                    if (Game.checkOnlyNeedDo()) {
                        Game.lightAllFights();
                        Game.onlyNeedDo();
                        if (needDo[x][y]) {
                            fight = 2;
                            firstClick(x, y);
                        } else kolClick--;
                    } else if (!Game.checkOnlyNeedDo()) {
                        Game.needDo();
                        if (needDo[x][y]) {
                            fight = 1;
                            firstClick(x, y);
                        } else kolClick--;
                    } else {
                        Game.needDo();
                        kolClick--;
                    }
                } else if (kolClick % 2 == 0) {
                    if ((x == currentX && y == currentY) ||
                            (!avail[x][y] && q[x][y] != currentPlayer)) {
                        Toast.makeText(getApplicationContext(), "Choose correct cell", Toast.LENGTH_SHORT).show();
                        kolClick--;
                    } else secondClick(x, y);
                }
            }
        }
    }

    public void firstClick(int x, int y){
        availFalse();
        lightCurrentCell(x, y);

        if (!king[x][y]) {
            if (fight == 1) {
                lightFreeCells_Usual(x, y);
                lightOccupiedCells_Usual(x, y);
            } else {
                lightOccupiedCells_Usual(x, y);
            }
        }
        else {
            if (fight == 1){
                lightFreeCells_King(x, y);
                lightOccupiedCells_King(x, y);
            }
            else {
                lightOccupiedCells_King(x, y);
            }
        }
    }

    public void secondClick(int x, int y){
        if (q[x][y] == currentPlayer && needDo[x][y]) {
            currentX = x;
            currentY = y;
            kolClick = 1;
            availFalse();


            Game.cleanLights();
            if (Game.checkOnlyNeedDo()) {
                Game.lightAllFights();
                Game.onlyNeedDo();
                if (needDo[x][y]) {
                    fight = 2;
                    firstClick(x, y);
                }
                else kolClick = 1;
            }
            else if (!Game.checkOnlyNeedDo()){
                Game.needDo();
                if (needDo[x][y]){
                    fight = 1;
                    firstClick(x, y);
                }
                else kolClick = 1;
            }
        }
        else if (avail[x][y]) {
            ok = false;

            if (q[x][y] == 0){


                if (Math.abs(currentX-x) > 1){
                    if (king[currentX][currentY]) {
                        king[x][y] = true;
                    }
                    king[currentX][currentY] = false;
                    swap(x,y);
                    q[x][y] = currentPlayer;
                }
                else if (Math.abs(currentX-x) == 1){
                    if (king[currentX][currentY]) {

                        if (currentPlayer == 1){
                            buttons[x][y].setBackgroundResource(R.drawable.white_king);
                        }
                        else {
                            buttons[x][y].setBackgroundResource(R.drawable.black_king);
                        }
                        king[x][y] = true;
                        king[currentX][currentY] = false;

                    }
                    else {

                        if (currentPlayer == 1){
                            buttons[x][y].setBackgroundResource(R.drawable.white_brown);
                        }
                        else {
                            buttons[x][y].setBackgroundResource(R.drawable.black_brown);
                        }

                    }
                    q[x][y] = currentPlayer;
                    q[currentX][currentY] = 0;
                }

            }
            availFalse();
            currentX = x;
            currentY = y;
            checkKing(x, y);
            Game.cleanLights();
            if (ok) {
                if (currentPlayer == 1){
                    Game.minusBlackCount();
                    txtView1.setText("White: " + Integer.toString(Game.returnWhiteCount()));
                    txtView2.setText("Black: " + Integer.toString(Game.returnBlackCount()));
                }
                else {
                    Game.minusWhiteCount();
                    txtView1.setText("White: " + Integer.toString(Game.returnWhiteCount()));
                    txtView2.setText("Black: " + Integer.toString(Game.returnBlackCount()));
                }
                if (Game.checkWin() != -1){
                    if (Game.checkWin() == 1) {
                        if (MenuActivity.REQUEST_CODE != 1) {
                            Toast.makeText(this.getApplicationContext(), "\"" + "WHITE" + "\" " + "is win!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            txtView1.setText("White : Win");
                            txtView2.setText("Black : Lose");
                        }
                    }
                    else {
                        if (MenuActivity.REQUEST_CODE != 1) {
                            Toast.makeText(this.getApplicationContext(), "\"" + "BLACK" + "\" " + "is win!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            txtView1.setText("White : Lose");
                            txtView2.setText("Black : Win");
                        }
                    }
                    finish();
                }
                Game.onlyNeedDo();
                if (needDo[currentX][currentY]) {
                    Log.d("bla7","1337" + currentPlayer + MenuActivity.REQUEST_CODE);
                    if (MenuActivity.REQUEST_CODE == 1 && currentPlayer == 2){
                        kolClick = 2;
                        currentPlayer = 2;
                        Bot bot = new Bot();
                        bot.chooseSecondClickOccupied(currentX,currentY);
                    }
                    else {
                        fight = 2;
                        kolClick = 1;
                        firstClick(currentX, currentY);
                    }
                }
                else {
                    kolClick = 0;
                    currentPlayer = 3 - currentPlayer;
                    if (MenuActivity.REQUEST_CODE == 1 && currentPlayer == 2){
                        Bot bot = new Bot();
                        bot.firstClick();
                    }
                }
                Game.lightAllFights();
            }
            else {
                if (Game.checkWin() != -1){

                    if (Game.checkWin() == 1) {
                        Toast.makeText(getApplicationContext(),"\"" + "WHITE" + "\" " + "is win!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"\"" + "BLACK" + "\" " + "is win!",Toast.LENGTH_LONG).show();
                    }
                    finish();
                }
                kolClick = 0;
                currentPlayer = 3 - currentPlayer;
                Game.lightAllFights();
                if (MenuActivity.REQUEST_CODE == 1 && currentPlayer == 2){
                    Bot bot = new Bot();
                    Bot.firstClick();
                }
            }
        }
        else {
            Game.lightAllFights();
            Toast.makeText(this,"Choose correct cell",Toast.LENGTH_SHORT).show();
        }
    }
    boolean ok;


    public void checkKing(int x, int y){
        if (currentPlayer == 1 && x == 0) king[x][y] = true;
        if (currentPlayer == 2 && x == 7) king[x][y] = true;
    }

    public void swap(int x, int y){
        int xx = currentX;
        int yy = currentY;

        if (xx > x && yy > y){
            while (xx > x && yy > y){
                if (q[xx][yy] == 3-currentPlayer) ok = true;
                q[xx][yy] = 0;
                king[xx][yy] = false;
                xx--;
                yy--;
            }
        }
        xx = currentX;
        yy = currentY;
        if (xx < x && yy > y){
            while (xx < x && yy > y){
                if (q[xx][yy] == 3-currentPlayer) ok = true;
                q[xx][yy] = 0;
                king[xx][yy] = false;
                xx++;
                yy--;
            }
        }

        xx = currentX;
        yy = currentY;
        if (xx < x && yy < y){
            while (xx < x && yy < y){
                if (q[xx][yy] == 3-currentPlayer) ok = true;
                q[xx][yy] = 0;
                king[xx][yy] = false;
                xx++;
                yy++;
            }
        }

        xx = currentX;
        yy = currentY;
        if (xx > x && yy < y){
            while (xx > x && yy < y){
                if (q[xx][yy] == 3-currentPlayer) ok = true;
                q[xx][yy] = 0;
                king[xx][yy] = false;
                xx--;
                yy++;
            }
        }
    }

    public static void lightCurrentCell(int x, int y){
        if (!king[x][y]){
            if (currentPlayer == 1) {
                buttons[x][y].setBackgroundResource(R.drawable.white_red_selector);
            }
            else {
                buttons[x][y].setBackgroundResource(R.drawable.black_red_selector);
            }
        }
        else {
            if (currentPlayer == 1){
                buttons[x][y].setBackgroundResource(R.drawable.white_king_red_selector);
            }
            else {
                buttons[x][y].setBackgroundResource(R.drawable.black_king_red_selector);
            }
        }
    }

    public static void lightFreeCells_Usual(int x, int y){
        if (currentPlayer == 1){
            if (x - 1 >= 0 && y - 1 >= 0 && q[x-1][y-1] == 0) {
                avail[x-1][y-1] = true;
                buttons[x-1][y-1].setBackgroundResource(R.drawable.free_brown_green_selector);
            }
            if (x - 1 >= 0 && y + 1 < 8 && q[x-1][y+1] == 0){
                avail[x-1][y+1] = true;
                buttons[x-1][y+1].setBackgroundResource(R.drawable.free_brown_green_selector);
            }
        }
        if (currentPlayer == 2){
            if (x + 1 < 8 && y + 1 < 8 && q[x+1][y+1] == 0){
                avail[x+1][y+1] = true;
                buttons[x+1][y+1].setBackgroundResource(R.drawable.free_brown_green_selector);
            }
            if (x + 1 < 8 && y - 1 >= 0 && q[x+1][y-1] == 0){
                avail[x+1][y-1] = true;
                buttons[x+1][y-1].setBackgroundResource(R.drawable.free_brown_green_selector);
            }
        }
    }

    public static void lightOccupiedCells_Usual(int x, int y){
        if (x - 2 >= 0 && y - 2 >= 0 && q[x-2][y-2] == 0 && q[x-1][y-1] == 3 - currentPlayer){
            avail[x-2][y-2] = true;
            buttons[x-2][y-2].setBackgroundResource(R.drawable.free_brown_green_selector);
        }
        if (x - 2 >= 0 && y + 2 < 8 && q[x-2][y+2] == 0 && q[x-1][y+1] == 3 - currentPlayer){
            avail[x-2][y+2] = true;
            buttons[x-2][y+2].setBackgroundResource(R.drawable.free_brown_green_selector);
        }
        if (x + 2 < 8 && y + 2 < 8 && q[x+2][y+2] == 0 && q[x+1][y+1] == 3 - currentPlayer){
            avail[x+2][y+2] = true;
            buttons[x+2][y+2].setBackgroundResource(R.drawable.free_brown_green_selector);
        }
        if (x + 2 < 8 && y - 2 >= 0 && q[x+2][y-2] == 0 && q[x+1][y-1] == 3 - currentPlayer){
            avail[x+2][y-2] = true;
            buttons[x+2][y-2].setBackgroundResource(R.drawable.free_brown_green_selector);
        }
    }

    public static void lightFreeCells_King(int x, int y){
        int xx = x - 1;
        int yy = y - 1;
        while (xx >= 0 && yy >= 0){
            if (q[xx][yy] != 0) break;
            avail[xx][yy] = true;
            buttons[xx][yy].setBackgroundResource(R.drawable.free_brown_green_selector);
            xx--;
            yy--;
        }

        xx = x - 1;
        yy = y + 1;
        while (xx >= 0 && yy < 8){
            if (q[xx][yy] != 0) break;
            avail[xx][yy] = true;
            buttons[xx][yy].setBackgroundResource(R.drawable.free_brown_green_selector);
            xx--;
            yy++;
        }

        xx = x + 1;
        yy = y - 1;
        while (xx < 8 && yy >= 0){
            if (q[xx][yy] != 0) break;
            avail[xx][yy] = true;
            buttons[xx][yy].setBackgroundResource(R.drawable.free_brown_green_selector);
            xx++;
            yy--;
        }

        xx = x + 1;
        yy = y + 1;
        while (xx < 8 && yy < 8){
            if (q[xx][yy] != 0) break;
            avail[xx][yy] = true;
            buttons[xx][yy].setBackgroundResource(R.drawable.free_brown_green_selector);
            xx++;
            yy++;
        }
    }

    public static void lightOccupiedCells_King(int x, int y){

        //-----------------------------// - // - //-------------------------------------------
        int xx = x - 1;
        int yy = y - 1;
        while (xx >= 0 && yy >= 0){
            if (q[xx][yy] == currentPlayer) {
                break;
            }
            if (q[xx][yy] == 3 - currentPlayer){
                xx--;
                yy--;
                while (xx >= 0 && yy >= 0 && q[xx][yy] == 0){
                    avail[xx][yy] = true;
                    buttons[xx][yy].setBackgroundResource(R.drawable.free_brown_green_selector);
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
            if (q[xx][yy] == currentPlayer) {
                break;
            }
            if (q[xx][yy] == 3 - currentPlayer){
                xx--;
                yy++;
                while (xx >= 0 && yy < 8 && q[xx][yy] == 0){
                    avail[xx][yy] = true;
                    buttons[xx][yy].setBackgroundResource(R.drawable.free_brown_green_selector);
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
            if (q[xx][yy] == currentPlayer) {
                break;
            }
            if (q[xx][yy] == 3 - currentPlayer){
                xx++;
                yy++;
                while (xx < 8 && yy < 8 && q[xx][yy] == 0){
                    avail[xx][yy] = true;
                    buttons[xx][yy].setBackgroundResource(R.drawable.free_brown_green_selector);
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
            if (q[xx][yy] == currentPlayer) {
                break;
            }
            if (q[xx][yy] == 3 - currentPlayer){
                xx++;
                yy--;
                while (xx < 8 && yy >= 0 && q[xx][yy] == 0){
                    avail[xx][yy] = true;
                    buttons[xx][yy].setBackgroundResource(R.drawable.free_brown_green_selector);
                    xx++;
                    yy--;
                }
                break;
            }
            xx++;
            yy--;
        }

    }

}
