package com.example.android.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by cayte on 11/5/15.
 */
public class EditStartButtonFrag extends Fragment{

    Button startGameBtn;
    Button editNamesBtn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.edit_start_button_frag, container, false);  //???is this right?

        startGameBtn = (Button) mView.findViewById(R.id.startGameBtn);

        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add method implementation to gameboard activity
                mListener.startNewGame();
            }
        });

        editNamesBtn = (Button) mView.findViewById(R.id.editNamesBtn);

        editNamesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add method implementation to gameboard activity
                mListener.editNames();
            }
        });



        return mView;
    }

    EditStartButtonFragListener mListener;

    @Override
    public void onAttach(Activity activity) {   //http://developer.android.com/training/basics/fragments/communicating.html
        super.onAttach(activity);
        try {
            mListener = (EditStartButtonFragListener) activity; //??? not sure i understand this
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement EnterNamesFragListener");
        }
    }

        //???do i actually need an interface here or can i just to things with button onClickListeners?
    public interface EditStartButtonFragListener {
        //TODO: define useful methods
        void startNewGame();
        void editNames();
    }



}
