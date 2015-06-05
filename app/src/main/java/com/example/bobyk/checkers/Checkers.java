package com.example.bobyk.checkers;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * Created by bobyk on 05/06/15.
 */
public class Checkers extends ActionBarActivity{

    private Game game;
    private Button[][] buttons = new Button[8][8];
    private TableLayout layout;

    public int kolClick;
    public int[][] q = new int[8][8];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        layout = (TableLayout) findViewById(R.id.main_l);
        buildGameField();
    }

    private void buildGameField() {
        game = new Game();
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

                button.setWidth(display.getWidth()/8);
                button.setHeight(display.getHeight()/13);

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
    }

    public class Listener implements View.OnClickListener {
        private int x = 0;
        private int y = 0;

        public Listener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void onClick(View view) {

        }
    }

}
