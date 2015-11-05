package com.example.android.tictactoe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by cayte on 11/4/15.
 */
public class BoardPagerAdapter extends FragmentPagerAdapter {
    int NumOfGamesOpen = 10; //??? TODO: where is this number going to come from / where will it be incremented?
    String p1;
  //  String p2;

    public BoardPagerAdapter(FragmentManager fm, String s1, String s2) {  //why do i need a constructor here?
        super(fm);
        p1 = s1;    //??? a stupid way to pass player names?
        p2 = s2;
    }

    @Override
    public int getCount() { //
        return NumOfGamesOpen;
    }

    @Override
    public Fragment getItem(int position) {
        return GameBoardFrag.newInstance(position, p1, p2); //??? why a new one?
    }


}
