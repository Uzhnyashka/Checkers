package com.example.bobyk.checkers;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by bobyk on 05/06/15.
 */
public class MenuActivity extends Activity implements View.OnClickListener{

    private Button btn1, btn2;
    public static int REQUEST_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.menu_activity);

        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:

                openOnePlayerActivity();

                 break;

            case R.id.button2:

                openTwoPlayersActivity();

                break;
        }
    }

    public void openTwoPlayersActivity(){
        REQUEST_CODE = 2;
        Intent intent = new Intent(this, Checkers.class);
        startActivity(intent);

    }

    public void openOnePlayerActivity(){
        REQUEST_CODE = 1;
        Intent intent = new Intent(this, Checkers.class);
        startActivity(intent);
    }
}
