package com.example.android.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cayte on 11/3/15.
 */
public class GameBoardFragment extends Fragment {
    GridLayout myGrid;
    boolean whoseMove = false; //false for X, true for O

    String player1name;
    String player2name;

    TextView player1;
    TextView player2;

    boolean isWinner = false;
    String whoWon = "";
    int count = 0;

    Button editNamesBtn;


    static GameBoardFragment newInstance(String p1, String p2) {     //???why static (tutorial said so, but can't set player names that way)? and in general???
        GameBoardFragment f = new GameBoardFragment();

        // Supply num input as an argument.

        //???why am i doing this exactly? - so i can get the data in the OnCreateView
        Bundle args = new Bundle();
        args.putString(Constants.PLAYER1, p1);
        args.putString(Constants.PLAYER2, p2);
        f.setArguments(args);

        return f;
    }


    public void updateName(String name1, String name2) {
        player1.setText(name1);
        player2.setText(name2);
        player1name = name1;
        player2name = name2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle b) {
        View mView = inflater.inflate(R.layout.game_board_frag, container, false);  //???is this right?


        Log.v(player1name + " onView ", "y");
        if (b != null) {
            Log.v("bundle in onView is ", b.toString());

            String ss = getArguments().getString("savedState");
            if (ss != null){
                char[] savedStateArr = ss.toCharArray();
                for (int i = 0; i < savedStateArr.length; i++) {
                    SquareButton sqBtn = (SquareButton) myGrid.getChildAt(i);
                    if (savedStateArr[i] == 'x') {
                        sqBtn.setImageResource(R.drawable.x);
                        sqBtn.setScaleType(ImageButton.ScaleType.FIT_XY);
                        sqBtn.setTag("x");
                        sqBtn.setEnabled(false);
                    }
                    if (savedStateArr[i] == 'o') {
                        sqBtn.setImageResource(R.drawable.o);
                        sqBtn.setScaleType(ImageButton.ScaleType.FIT_XY);
                        sqBtn.setTag("o");
                        sqBtn.setEnabled(false);

                    }
                }}
        }





        //??? not sure i understand how putting things in a bundle in the constructor works with me getting them here.
        player1name = getArguments().getString(Constants.PLAYER1);
        player2name = getArguments().getString(Constants.PLAYER2);

        player1 = (TextView) mView.findViewById(R.id.player1name);
        player1.setText(player1name);
        player2 = (TextView) mView.findViewById(R.id.player2name);
        player2.setText(player2name);

        myGrid = (GridLayout) mView.findViewById(R.id.myGrid);
        for (int i = 1; i <= 9; i++) {
            final SquareButton myBtn = new SquareButton(getActivity());  //??? why final?
            myBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!whoseMove) {
                        v.setTag("x");
                    } else {
                        v.setTag("o");
                    }
                    makeMove(myBtn);
                    checkWin();
                }
            });
            myGrid.addView(myBtn);
        }

        editNamesBtn = (Button) mView.findViewById(R.id.editNamesBtn);
        editNamesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.makeEditNamesDialog(player1name, player2name);
            }
        });
        return mView;
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.v("wincheckR is ", winCheckR);
        char[] savedStateArr = winCheckR.toCharArray();
        for (int i = 0; i < savedStateArr.length; i++){
            SquareButton sqBtn = (SquareButton) myGrid.getChildAt(i);
            if (savedStateArr[i] == 'x'){
                sqBtn.setImageResource(R.drawable.x);
                sqBtn.setScaleType(ImageButton.ScaleType.FIT_XY);
                sqBtn.setTag("x");
                sqBtn.setEnabled(false);
            }
            if (savedStateArr[i] == 'o'){
                sqBtn.setImageResource(R.drawable.o);
                sqBtn.setScaleType(ImageButton.ScaleType.FIT_XY);
                sqBtn.setTag("o");
                sqBtn.setEnabled(false);

            }
        }
    }

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        Log.v(player1name + " onCreate", "y");
        if (b != null) {
            Log.v("bundle in onVreate is ", b.toString());

            String ss = getArguments().getString("savedState");
            if (ss != null){
            char[] savedStateArr = ss.toCharArray();
            for (int i = 0; i < savedStateArr.length; i++) {
                SquareButton sqBtn = (SquareButton) myGrid.getChildAt(i);
                if (savedStateArr[i] == 'x') {
                    sqBtn.setImageResource(R.drawable.x);
                    sqBtn.setScaleType(ImageButton.ScaleType.FIT_XY);
                    sqBtn.setTag("x");
                    sqBtn.setEnabled(false);
                }
                if (savedStateArr[i] == 'o') {
                    sqBtn.setImageResource(R.drawable.o);
                    sqBtn.setScaleType(ImageButton.ScaleType.FIT_XY);
                    sqBtn.setTag("o");
                    sqBtn.setEnabled(false);

                }
            }}
        }
    }

    @Override
    public void onSaveInstanceState (Bundle b){
        super.onSaveInstanceState(b);
        Log.v(player1name + " onsaveinsst wincheck: ", winCheckR);
        b.putString("savedState", winCheckR);
        Log.v(player1name +" onsaveinsst b is", b.toString());
    }



    private void makeMove (SquareButton sqBtn){
        if (!whoseMove) {
            sqBtn.setImageResource(R.drawable.x);
            sqBtn.setScaleType(ImageButton.ScaleType.FIT_XY);
            whoseMove = !whoseMove;
        } else{
            sqBtn.setImageResource(R.drawable.o);
            sqBtn.setScaleType(ImageButton.ScaleType.FIT_XY);
            whoseMove = !whoseMove;
        }
        sqBtn.setEnabled(false);
    }

    String winCheckR = "";

    private void checkWin (){
        winCheckR = "";
        String winCheckC = "";
        String winCheckD = "";
        String winCheckAD = "";
        String wx = "xxx";
        String wo = "ooo";

        for (int i = 0; i<9; i++) { //check rows for win
            SquareButton btn = (SquareButton) myGrid.getChildAt(i);
            if (btn.getTag() != null) {
                String xoro = btn.getTag().toString();
                winCheckR = winCheckR + xoro;
            } else {
                winCheckR = winCheckR + "b";
            }
        }

        int i = 0;
        while (i < 9) { //check columns  //???fugly - is there a better way?
            SquareButton btn = (SquareButton) myGrid.getChildAt(i);
            if (btn.getTag() != null) {
                String xoro = btn.getTag().toString();
                winCheckC = winCheckC + xoro;
            } else {
                winCheckC = winCheckC + "b";
            }
            i = i + 3;
            if (i > 8 && i < 11){
                i = i - 8;
            }
        }

        for (int j = 0; j<9; j = j + 4) { //check Diagonal for win  //??? - why doesn't i=+4 work?
            SquareButton btn = (SquareButton) myGrid.getChildAt(j);
            if (btn.getTag() != null) {
                String xoro = btn.getTag().toString();
                winCheckD = winCheckD + xoro;
            } else {
                winCheckD = winCheckD + "b";
            }
        }

        for (int j = 2; j<7; j = j + 2) { //check antiDiagonal for win
            SquareButton btn = (SquareButton) myGrid.getChildAt(j);
            if (btn.getTag() != null) {
                String xoro = btn.getTag().toString();
                winCheckAD = winCheckAD + xoro;
            } else {
                winCheckAD = winCheckAD + "b";
            }
        }

        winContains(winCheckR, wx, wo);
        winContains(winCheckC, wx, wo);
        winContains(winCheckD, wx, wo);
        winContains(winCheckAD, wx, wo);
        if (count == 9 && !isWinner) {
            whoWon = "NO ONE won - It's a draw!";
            mListener.makeGameOverDialog(whoWon, player1name, player2name);

            //getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            isWinner = true;
        }
    }

    private void winContains(String win, String wx, String wo) {
        if (!isWinner) {
            String winPatternX = "^(.{0}|.{3}|.{6})" + wx;
            String winPatternO = "^(.{0}|.{3}|.{6})" + wo;

            Pattern rX = Pattern.compile(winPatternX);
            Pattern rO = Pattern.compile(winPatternO);

            Matcher mX = rX.matcher(win);
            Matcher mO = rO.matcher(win);

            boolean winX = false;
            boolean winO = false;

            if (mX.find()) {
                winX = true;
                whoWon = player1name + " won";
            }
            if (mO.find()) {
                winO = true;
                whoWon = player2name + " won";
            }

            count = 0;
            for (int i = 0; i < 9; i++) {
                //check if it's a draw
                SquareButton btn = (SquareButton) myGrid.getChildAt(i);
                if (!btn.isEnabled()) {
                    count++;
                }
            }

            if (winX || winO) {
                Toast.makeText(getActivity(), whoWon + "won", Toast.LENGTH_LONG).show(); //TODO: don't forget to get rid of this
                Log.v("someone won", "winwin");

                mListener.makeGameOverDialog(whoWon, player1name, player2name);

                //getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();//communicates to the GameBoardActivity Activity (tells it who won and has it make the dialog
                for (int i = 0; i < 9; i++) {
                    SquareButton btn = (SquareButton) myGrid.getChildAt(i);
                    btn.setEnabled(false);
                }
                isWinner = true;
            }
        }
    }


    GameBoardFragListener mListener;

    @Override
    public void onAttach(Context context) {   //http://developer.android.com/training/basics/fragments/communicating.html
        super.onAttach(context);
        try {
            mListener = (GameBoardFragListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement EnterNamesFragListener");
        }
    }

    public interface GameBoardFragListener {
        //TODO: define useful methods
        void makeGameOverDialog(String s1, String s2, String s3);//??? is this reasonable?
        void makeEnterNamesDialog();
        void makeEditNamesDialog(String p1, String p2);

    }
}
