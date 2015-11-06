package com.example.android.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String player1name; //= findViewById(R.id.player1name).toString();
    String player2name; //= findViewById(R.id.player2name).toString();
    EditText player1;
    EditText player2;
    Button startGamebtn;

    //TODO - pretty transition to gameboards
    //TODO - can play several games at once?
    //TODO - can choose size of grid?
    //TODO - save player's names for future autocomplete
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player1 = (EditText) findViewById(R.id.player1name);
        player2 = (EditText) findViewById(R.id.player2name);
        startGamebtn = (Button) findViewById(R.id.startGamebtn);

        startGamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1name = player1.getText().toString();
                player2name = player2.getText().toString();
                if (player1name.isEmpty() || player2name.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Please Enter Your Name", Toast.LENGTH_LONG).show();
                } else {
                    Intent mIntent = new Intent(getBaseContext(), GameBoardActivity.class);
                    mIntent.putExtra(Constants.PLAYER1, player1name);
                    mIntent.putExtra(Constants.PLAYER2, player2name);
                    startActivity(mIntent);
                }

            }
        });
    }

    @Override
    public void onPause(){

        player1.getText().clear();
        player2.getText().clear();
        player1.requestFocus();
        super.onPause();
    }
}
