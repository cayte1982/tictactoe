package com.example.android.tictactoe;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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

    //TODO - different layout for landscape
    //TODO - save game state?
    //TODO - save screen shots of last 10 games?

public class GameBoard extends FragmentActivity implements GameDialog.NoticeDialogListener,
        EnterNamesFrag.EnterNamesFragListener, GameBoardFrag.GameBoardFragListener {

    BoardPagerAdapter mPagerAdapter;
    ViewPager mViewPager;
    String player1name;
    String player2name;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);

        Intent mIntent = getIntent();
        player1name = mIntent.getStringExtra(Constants.PLAYER1);
        player2name = mIntent.getStringExtra(Constants.PLAYER2);


        // ViewPager and its adapters use support library fragments, so use getSupportFragmentManager.
        mPagerAdapter = new BoardPagerAdapter(getSupportFragmentManager(), player1name, player2name); //??? is there a better way to get player names into the frags?
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);
    }
        //TODO - plays a sound or something


    @Override
    public void onDialogSameGameClick(String player1name, String player2name){
        finish();//??? how to destroy the current fragment?

        //TODO: this is no longer starting a new activity, but rather creates a new fragment (and adds it to the ViewPager
       /** Intent mIntent = new Intent(getBaseContext(), GameBoard.class);
        mIntent.putExtra(Constants.PLAYER1, player2name); //switch player sides
        mIntent.putExtra(Constants.PLAYER2, player1name);
        startActivity(mIntent);**/

        Bundle mBundle = new Bundle();
        mBundle.putString(Constants.PLAYER1, player2name); //switch player sides
        mBundle.putString(Constants.PLAYER2, player1name);

        GameBoardFrag newGame = new GameBoardFrag();
        newGame.setArguments(mBundle);
    }

    @Override
    public void onDialogDiffGameClick(){
        finish();
        //TODO: destroy fragment and go to EnterNamesFrag
        //TODO: this is where we include a flag to EnterNamesFrag (new game vs. edit names)
    }

    @Override
    public void startNewGameFrag(){

    }

    @Override
    public void returnToGameWithEditedNames(){

    };

    @Override
    public void makeDialog (String whoWon, String player1name, String player2name){
        //make dialog
        //set arguments to pass info into the dialog
        GameDialog gameEndDialog = new GameDialog();
        Bundle args = new Bundle();
        args.putString("whoWonThisGame", whoWon); //TODO: this won't work now, so get info from GameFrag
        args.putString(Constants.PLAYER1, player1name); //TODO: this won't work now, so get info from GameFrag
        args.putString(Constants.PLAYER2, player2name); //TODO: this won't work now, so get info from GameFrag
        gameEndDialog.setArguments(args);

        gameEndDialog.show(this.getSupportFragmentManager(), "GameOver");

    }




}

