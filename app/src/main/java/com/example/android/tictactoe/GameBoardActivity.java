package com.example.android.tictactoe;

import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.FragmentActivity;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;

/**
 * Created by cayte on 10/21/15.
 */

    //TODO - different layout for landscape
    //TODO - save game state?
    //TODO - save screen shots of last 10 games?

public class GameBoardActivity extends FragmentActivity implements GameDialog.NoticeDialogListener,
        EnterNamesDialog.EnterNamesFragListener, GameBoardFragment.GameBoardFragListener, EditNamesDialog.EditNamesDialogListener {
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

        // ViewPager and its adapters use support library fragments, so use getSupportFragmentManager.
        mPagerAdapter = new BoardPagerAdapter(getSupportFragmentManager(), player1name, player2name, this); //??? is there a better way to get player names into the frags?

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(10);

        mFab = (FloatingActionButton) findViewById(R.id.newgame);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //does NOT add game
                makeEnterNamesDialog();

            }
        });
    }
        //TODO - plays a sound or something


    @Override
    public void onDialogSameGameClick(String player1name, String player2name){
        mPagerAdapter.deleteGame(((GameBoardFragment)mPagerAdapter.getItem(mViewPager.getCurrentItem())));
       /** Intent mIntent = new Intent(getBaseContext(), GameBoardActivity.class);
        mIntent.putExtra(Constants.PLAYER1, player2name); //switch player sides
        mIntent.putExtra(Constants.PLAYER2, player1name);
        startActivity(mIntent);**/

       /** Bundle mBundle = new Bundle();
        mBundle.putString(Constants.PLAYER1, player2name); //switch player sides
        mBundle.putString(Constants.PLAYER2, player1name);
        mBundle.putString("fragName", "test");

        GameBoardFragment newGame = new GameBoardFragment();
        newGame.setArguments(mBundle);**/
        mPagerAdapter.addGame(player2name, player1name);
        mViewPager.setCurrentItem(mPagerAdapter.getCount()-1);
    }

    @Override
    public void onDialogDiffGameClick(){
        mPagerAdapter.deleteGame(((GameBoardFragment)mPagerAdapter.getItem(mViewPager.getCurrentItem())));
        makeEnterNamesDialog();
        //destroy fragment and go to EnterNamesDialog
    }




    @Override
    public void makeGameOverDialog (String whoWon, String player1name, String player2name){
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
    public void makeEnterNamesDialog(){ //from GameBoardFrag
        //start new game from within an ongoing game
        //needs to bring up EnterNamesDialog
        EnterNamesDialog newGameDialog = new EnterNamesDialog();
        newGameDialog.show(this.getSupportFragmentManager(), "NewGame");
    };

    @Override
    public void startNewGameFrag(String p1name, String p2name) { //from EnterNamesFrag
        mPagerAdapter.addGame(p1name, p2name);
        mViewPager.setCurrentItem(mPagerAdapter.getCount()-1);

    }

    @Override
    public void makeEditNamesDialog(String oldP1name, String oldP2name){
        //needs to have old player names show up
        EditNamesDialog mEditNamesDialog = new EditNamesDialog();

        //make dialog
        //set arguments to pass info into the dialog
        Bundle args = new Bundle();
        args.putString("oldP1name", oldP1name);
        args.putString("oldP2name", oldP2name);
        mEditNamesDialog.setArguments(args);

       // EditText p1 = (EditText) findViewById(R.id.play1name);//???how to deal with multiple ID's of the same name in different layout files
      //  p1.setText(oldP1name);

      //  EditText p2 = (EditText) findViewById(R.id.play2name);//???how to deal with multiple ID's of the same name in different layout files
      //  p2.setText(oldP1name);

        mEditNamesDialog.show(this.getSupportFragmentManager(), "EditNames");

    }

    @Override
    public void resumeGame (String p1, String p2){
        //TODO???how to update game
        ((GameBoardFragment)mPagerAdapter.getItem(mViewPager.getCurrentItem())).updateName(p1, p2);

    };



}

