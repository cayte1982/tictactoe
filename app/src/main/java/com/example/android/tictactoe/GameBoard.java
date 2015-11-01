package com.example.android.tictactoe;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cayte on 10/21/15.
 */

//TODO - add player names to sides
    //TODO - different layout for landscape
    //TODO - save game state?
    //TODO - save screen shots of last 10 games?

public class GameBoard extends FragmentActivity implements NoticeDialogListener{

    GridLayout myGrid;
    boolean whoseMove = false; //false for X, true for O

    String player1name;
    String player2name;

    TextView player1;
    TextView player2;

    boolean isWinner = false;
    String whoWon = "";
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboard);

        player1name = getIntent().getExtras().getString(Constants.PLAYER1);
        player2name = getIntent().getExtras().getString(Constants.PLAYER2);

        player1 = (TextView) findViewById(R.id.player1name);
        player1.setText(player1name);
        player2 = (TextView) findViewById(R.id.player2name);
        player2.setText(player2name);


        myGrid = (GridLayout) findViewById(R.id.myGrid);
        for (int i = 1; i <= 9; i++) {
            final SquareButton myBtn = new SquareButton(getBaseContext());  //??? why final?
            myBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!whoseMove) {
                        v.setTag("x");
                    } else {
                        v.setTag("o");
                    }
                    makeMove(myBtn);
                    checkWin();
                }
            });
            myGrid.addView(myBtn);
        }
    }

    private void makeMove (SquareButton sqBtn){
        if (!whoseMove) {
            sqBtn.setImageResource(R.drawable.x);
            sqBtn.setScaleType(ImageButton.ScaleType.FIT_XY);
            whoseMove = !whoseMove;
        } else{
            sqBtn.setImageResource(R.drawable.o);
            sqBtn.setScaleType(ImageButton.ScaleType.FIT_XY);
            whoseMove = !whoseMove;
        }
        sqBtn.setEnabled(false);

    }

    private void checkWin (){

        String winCheckR = ""; //OK to initialize here?
        String winCheckC = "";
        String winCheckD = "";
        String winCheckAD = "";
        String wx = "xxx";
        String wo = "ooo";

        for (int i = 0; i<9; i++) { //check rows for win
            SquareButton btn = (SquareButton) myGrid.getChildAt(i);
            if (btn.getTag() != null) {
                String xoro = btn.getTag().toString();
                Log.v("xoro contains", xoro);
                winCheckR = winCheckR + xoro;
            } else {
                winCheckR = winCheckR + "b";
                Log.v("xoro contains", "null");
            }
        }
        Log.v("list of positions is: ", winCheckR.toString());

        int i = 0;
       while (i < 9) { //check columns  //???fugly - is there a better way?
           Log.v("value of i is", Integer.toString(i));
           SquareButton btn = (SquareButton) myGrid.getChildAt(i);
           if (btn.getTag() != null) {
               String xoro = btn.getTag().toString();
               Log.v("xoroC contains", xoro);
               winCheckC = winCheckC + xoro;
           } else {
               winCheckC = winCheckC + "b";
               Log.v("xoroC contains", "null");
           }
           i = i + 3;
           if (i > 8 && i < 11){
               i = i - 8;
           }
       }
        Log.v("list of Vert pos is: ", winCheckC.toString());

        for (int j = 0; j<9; j = j + 4) { //check Diagonal for win  //??? - why doesn't i=+4 work?
            SquareButton btn = (SquareButton) myGrid.getChildAt(j);
            if (btn.getTag() != null) {
                String xoro = btn.getTag().toString();
                Log.v("xoro contains", xoro);
                winCheckD = winCheckD + xoro;
            } else {
                winCheckD = winCheckD + "b";
                Log.v("xoro contains", "null");
            }
        }
        Log.v("list of Diag pos is: ", winCheckD.toString());


        for (int j = 2; j<7; j = j + 2) { //check antiDiagonal for win
            SquareButton btn = (SquareButton) myGrid.getChildAt(j);
            if (btn.getTag() != null) {
                String xoro = btn.getTag().toString();
                Log.v("xoro contains", xoro);
                winCheckAD = winCheckAD + xoro;
            } else {
                winCheckAD = winCheckAD + "b";
                Log.v("xoro contains", "null");
            }
        }
        Log.v("list of ADiag pos is: ", winCheckAD.toString());

        winContains(winCheckR, wx, wo);
        winContains(winCheckC, wx, wo);
        winContains(winCheckD, wx, wo);
        winContains(winCheckAD, wx, wo);
        if (count == 9 && !isWinner) {
            whoWon = "NO ONE won - It's a draw!";
            makeDialog(whoWon);
            isWinner = true;
        }

    }

    private void winContains(String win, String wx, String wo){

        if (!isWinner) {
            String winPatternX = "^(.{0}|.{3}|.{6})" + wx;
            String winPatternO = "^(.{0}|.{3}|.{6})" + wo;

            Pattern rX = Pattern.compile(winPatternX);
            Pattern rO = Pattern.compile(winPatternO);

            Matcher mX = rX.matcher(win);
            Matcher mO = rO.matcher(win);

            boolean winX = false;
            boolean winO = false;



            if (mX.find()) {
                winX = true;
                whoWon = player1name + " won";
            }
            if (mO.find()) {
                winO = true;
                whoWon = player2name + " won";
            }



            count = 0;
            for (int i = 0; i < 9; i++) {
                //check if it's a draw
                SquareButton btn = (SquareButton) myGrid.getChildAt(i);
                if (!btn.isEnabled()) {
                    count++;
                }
            }

            if (winX || winO) {
                Toast.makeText(getBaseContext(), whoWon + "won", Toast.LENGTH_LONG).show();
                Log.v("someone won", "winwin");
                makeDialog(whoWon);
                for (int i = 0; i < 9; i++) {
                    SquareButton btn = (SquareButton) myGrid.getChildAt(i);
                    btn.setEnabled(false);
                }
                isWinner = true;
            }

        }

        //
        //TODO -turn toast into dialog
        // acknowledging starts a new game (players sides reversed)
        // adds a win to record keeping (kept in the menu??)
        //TODO - plays a sound or something
    }

    private void makeDialog(String whoWon){
        //make dialog
        //set arguments to pass info into the dialog
        GameDialog gameEndDialog = new GameDialog();
        Bundle args = new Bundle();
        args.putString("whoWonThisGame", whoWon);
        gameEndDialog.setArguments(args);

        gameEndDialog.show(this.getSupportFragmentManager(), "GameOver");

    }

    @Override
    public void onDialogSameGameClick(){
        finish();
        Intent mIntent = new Intent(getBaseContext(), GameBoard.class);
        mIntent.putExtra(Constants.PLAYER1, player2name); //switch player sides
        mIntent.putExtra(Constants.PLAYER2, player1name);
        startActivity(mIntent);
    };

    @Override
    public void onDialogDiffGameClick(){
        finish();
    };





}

