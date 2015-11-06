package com.example.android.tictactoe;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by cayte on 10/21/15.
 */

    //TODO - different layout for landscape
    //TODO - save game state?
    //TODO - save screen shots of last 10 games?

public class GameBoardActivity extends FragmentActivity implements GameDialog.NoticeDialogListener,
        EnterNamesFrag.EnterNamesFragListener, GameBoardFragment.GameBoardFragListener,
        EditStartButtonFrag.EditStartButtonFragListener{
    FloatingActionButton mFab;
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

        //??? TODO - how do I add the editstart frag to this layout???
        // ViewPager and its adapters use support library fragments, so use getSupportFragmentManager.
        mPagerAdapter = new BoardPagerAdapter(getSupportFragmentManager()); //??? is there a better way to get player names into the frags?
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);
        mFab = (FloatingActionButton) findViewById(R.id.newgame);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPagerAdapter.addGame("test",player1name,player2name);
            }
        });
    }
        //TODO - plays a sound or something


    @Override
    public void onDialogSameGameClick(String player1name, String player2name){
        finish();//??? how to destroy the current fragment?

        //TODO: this is no longer starting a new activity, but rather creates a new fragment (and adds it to the ViewPager
       /** Intent mIntent = new Intent(getBaseContext(), GameBoardActivity.class);
        mIntent.putExtra(Constants.PLAYER1, player2name); //switch player sides
        mIntent.putExtra(Constants.PLAYER2, player1name);
        startActivity(mIntent);**/

        Bundle mBundle = new Bundle();
        mBundle.putString(Constants.PLAYER1, player2name); //switch player sides
        mBundle.putString(Constants.PLAYER2, player1name);

        GameBoardFragment newGame = new GameBoardFragment();
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

    @Override
    public void startNewGame(){
        //start new game from within an ongoing game

    };

    @Override
    public void editNames(){

    };



}

