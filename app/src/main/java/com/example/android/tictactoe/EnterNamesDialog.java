package com.example.android.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by cayte on 11/2/15.
 */
public class EnterNamesDialog extends DialogFragment {
    String player1name; //= findViewById(R.id.player1name).toString();
    String player2name; //= findViewById(R.id.player2name).toString();
    EditText player1;
    EditText player2;
    Button startGamebtn;
    Context c;
    DialogFragment d;

    public Dialog onCreateDialog (Bundle savedInstanceState) {

        final AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity()); //??? why final

        LayoutInflater inflater = getActivity().getLayoutInflater();
        c = getActivity();
        d = this;
        View mView = inflater.inflate(R.layout.activity_main, null);
        player1 = (EditText) mView.findViewById(R.id.player1name);
        player2 = (EditText) mView.findViewById(R.id.player2name);
        startGamebtn = (Button) mView.findViewById(R.id.startGamebtn);

        startGamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: include logic regarding whether this is editing names or starting a new game
                player1name = player1.getText().toString();
                player2name = player2.getText().toString();
                if (player1name.isEmpty() || player2name.isEmpty()) {
                    Toast.makeText(c, "Please Enter Your Name", Toast.LENGTH_LONG).show();
                } else {
                    //create a new fragment and add it to the game activity
                    //this is where the interface method should get called (which is defined in the base activity.
                    mListener.startNewGameFrag(player1name, player2name);
                    d.dismiss();
                }

            }
        });

        myBuilder.setView(mView);

        return myBuilder.create();
    }

    EnterNamesFragListener mListener;

    @Override
    public void onAttach(Activity activity) {   //http://developer.android.com/training/basics/fragments/communicating.html
        super.onAttach(activity);
        try {
            mListener = (EnterNamesFragListener) activity; //??? not sure i understand this
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement EnterNamesFragListener");
        }
    }

    public interface EnterNamesFragListener {
        //TODO: define useful methods
        void startNewGameFrag(String p1, String p2);
    }


    @Override
    public void onPause(){
        player1.getText().clear();
        player2.getText().clear();
        player1.requestFocus();
        super.onPause();
    }

}
