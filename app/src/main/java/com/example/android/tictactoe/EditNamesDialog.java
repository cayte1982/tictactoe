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
public class EditNamesDialog extends DialogFragment {
    String player1name;
    String player2name;
    EditText player1;
    EditText player2;
    Button resumeGameBtn;
    Context c;

    public Dialog onCreateDialog (Bundle savedInstanceState) {

        final AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity()); //??? why final

        LayoutInflater inflater = getActivity().getLayoutInflater();
        c = getActivity();
        View mView = inflater.inflate(R.layout.edit_names_dialog, null);
        player1 = (EditText) mView.findViewById(R.id.play1name);
        player2 = (EditText) mView.findViewById(R.id.play2name);

        Bundle args = getArguments();
        player1.setText(args.getString("oldP1name"));
        player2.setText(args.getString("oldP2name"));


        resumeGameBtn = (Button) mView.findViewById(R.id.ResumeGameBtn);

        resumeGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1name = player1.getText().toString();
                player2name = player2.getText().toString();
                if (player1name.isEmpty() || player2name.isEmpty()) {
                    Toast.makeText(c, "Please Enter Your Name", Toast.LENGTH_LONG).show();
                } else {
                    mListener.resumeGame(player1name, player2name);
                    dismiss();
                }

            }
        });

        myBuilder.setView(mView);

        return myBuilder.create();
    }

    EditNamesDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {   //http://developer.android.com/training/basics/fragments/communicating.html
        super.onAttach(activity);
        try {
            mListener = (EditNamesDialogListener) activity; //??? not sure i understand this
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement EnterNamesFragListener");
        }
    }

    public interface EditNamesDialogListener {
        void resumeGame(String p1, String p2);
    }


    @Override
    public void onPause(){
        player1.getText().clear();
        player2.getText().clear();
        player1.requestFocus();
        super.onPause();
    }

}
