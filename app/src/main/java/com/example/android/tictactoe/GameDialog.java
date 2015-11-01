package com.example.android.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cayte on 10/23/15.
 */
public class GameDialog extends DialogFragment {

    DialogFragment d;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        d = this;
       // List<String> listItems = new ArrayList<String>();
       // listItems.add("Start a New Game (same opponent)");
        //listItems.add("Start a New Game (new opponent)");

        this.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_NoActionBar_TranslucentDecor);

        //final CharSequence[] endGameChoices = listItems.toArray(new CharSequence[listItems.size()]);
        // because it's used in the OnClick, which is asynchronous, I don't want this to be alterable so it doesn't cause a runtime exception

        final AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity()); //??? why final
        //final because it's possible that the activity gets destroyed whole the dialog is being created and the dialog needs the activity's context
        // so by declaring the builder final, it locks in the activity context.

        //myBuilder.setTitle("Please Choose What To Do Next");

        LayoutInflater inflater = getActivity().getLayoutInflater();

        //getting arguments that were set in gameboard.java when creating the dialog
        Bundle args = getArguments();
        String whoWon = args.getString("whoWonThisGame");

        View myView = inflater.inflate(R.layout.game_over_dialog, null);
        TextView winner = (TextView) myView.findViewById(R.id.whoWonText);
        winner.setText(whoWon);

        //creating buttons in xml because it follows the MVC pattern.
        Button sameOpp = (Button) myView.findViewById(R.id.newGameSameOpp);
        sameOpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                mListener.onDialogSameGameClick();
            }
        });

        Button diffOpp = (Button) myView.findViewById(R.id.newGameDiffOpp);
        diffOpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                mListener.onDialogDiffGameClick();
            }
        });

        setCancelable(false);

        myBuilder.setView(myView);

      /*  myBuilder.setItems(endGameChoices, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int choice){
                switch (choice){
                    case 0: mListener.onDialogSameGameClick();
                    case 1: mListener.onDialogDiffGameClick();
                }

            }
        });
        */

        return myBuilder.create();
    }

    NoticeDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

}
