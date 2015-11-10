package com.example.android.tictactoe;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    Bundle args;
   @Override
    public int getItemPosition(Object item) {
      //  if (!mGames.contains(item)) {
      if (mGames.size() >0){
       for (GameBoardFragment gf : mGames) {
           GridLayout grid = gf.getGrid();
           args = new Bundle();
           try {
               args.putByteArray("Obj_byte_array", object2Bytes(grid));

           } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();

           }
           ((GameBoardFragment) item).onSaveInstanceState(args);

       }}
            return POSITION_NONE;
       // }
    //    return mGames.indexOf(item);
    }

    static public byte[] object2Bytes( Object o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        return baos.toByteArray();
    }

    static public Object bytes2Object( byte raw[] )
            throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream( raw );
        ObjectInputStream ois = new ObjectInputStream( bais );
        Object o = ois.readObject();
        return o;
    }

    @Override
    public Fragment getItem(int position) {
        GridLayout grid = null;
        if (mGames.size() >1){
        for (GameBoardFragment gf : mGames){
            try {
                 grid = (GridLayout)bytes2Object(args.getByteArray("Obj_byte_array"));
              //  gf.setGrid(grid);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            GameBoardFragment.newInstance("bbb", "kkk", grid);

        }}

        return mGames.get(position);

    }

    public void addGame(String n1, String n2) {
        if (mGames.size() <=5) {
            GameBoardFragment gf = GameBoardFragment.newInstance(n1, n2);
           // mFm.beginTransaction().add(gf, "blah'").commit();
          //  gf.getActivity().getSupportFragmentManager().beginTransaction().add(gf, "b").commit();

            mGames.add(gf);
            notifyDataSetChanged();
            Log.v("id", "" + gf.getId());
        }
        else {//make toast
            Toast.makeText(mContext, "Too many games open... Can have 5 max.", Toast.LENGTH_LONG ).show();
        }
    }

    public void deleteGame(GameBoardFragment gf) {
        //Fragment f = mFm.findFragmentByTag(gamename);
        //mFm.beginTransaction().remove(gf).commit();
       // gf.getActivity().getSupportFragmentManager().beginTransaction().remove(gf).commit();
        //destroyItem(, getItemPosition(gf), gf);
        mGames.remove(gf);
        notifyDataSetChanged();
    }


}
