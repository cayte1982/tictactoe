package com.example.android.tictactoe;

import android.app.Application;
import android.widget.Toast;

/**
 * Created by cayte on 10/22/15.
 */
public class TicTacToe11 extends Application{

    @Override
    public void onCreate(){
        super.onCreate();
        Toast.makeText(this,"application just got started", Toast.LENGTH_LONG).show();
    }
}
