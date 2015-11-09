package com.example.android.tictactoe;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by cayte on 11/4/15.
 */
public class BoardPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<GameBoardFragment> mGames;
    FragmentManager mFm;
    Context mContext;

    public BoardPagerAdapter(FragmentManager fm, String p1, String p2, Context c) {  //why do i need a constructor here?
        super(fm);
        mContext = c;
        mFm = fm;
        mGames = new ArrayList<GameBoardFragment>();
       // mGames.add(GameBoardFragment.newInstance(p1, p2)); //creates the first game with names from main activity
        addGame(p1, p2);
        //???should this use addGame method instead - that way it will have a name to use later for deletion purposes???
    }

    @Override
    public int getCount() { //
        return mGames.size();
    }

   @Override
    public int getItemPosition(Object item) {
        if (!mGames.contains(item)) {
            return POSITION_NONE;
        }
        return POSITION_UNCHANGED;
    }

    @Override
    public Fragment getItem(int position) {
        return mGames.get(position);
    }

    public void addGame(String n1, String n2) {
        if (mGames.size() <=5) {
            GameBoardFragment gf = GameBoardFragment.newInstance(n1, n2);
            //mFm.beginTransaction().add(gf,gamename).commit();
            mGames.add(gf);
            notifyDataSetChanged();
        }
        else {//make toast
            Toast.makeText(mContext, "Too many games open... Can have 5 max.", Toast.LENGTH_LONG ).show();
        }
    }

    public void deleteGame(GameBoardFragment gf) {
        //Fragment f = mFm.findFragmentByTag(gamename);
        //mFm.beginTransaction().remove(f).commit();
        mGames.remove(gf);
    //    gf.getActivity().getSupportFragmentManager().beginTransaction().remove(gf).commit();
        notifyDataSetChanged();
    }


}
