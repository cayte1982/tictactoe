package com.example.android.tictactoe;

import android.util.Log;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ssrinivasan on 11/5/15.
 */
public class Utils {


    public static boolean checkWin (GridLayout mGrid, String p1name, String p2name, GameBoardFragment.GameBoardFragListener mListen){
        String winCheckR = "";
        String winCheckC = "";
        String winCheckD = "";
        String winCheckAD = "";
        String wx = "xxx";
        String wo = "ooo";

        GameBoardFragment.GameBoardFragListener mListener = mListen;
        String whoWon = "";
        String player1name = p1name;
        String player2name = p2name;
        GridLayout myGrid = mGrid;
        boolean isWinner = false;
        int count = 0;

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
            Log.v("value of i is", Integer.toString(i));
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

        winContains(winCheckR, wx, wo, isWinner, whoWon, player1name, player2name, myGrid);
        winContains(winCheckC, wx, wo,isWinner, whoWon, player1name, player2name, myGrid);
        winContains(winCheckD, wx, wo, isWinner, whoWon, player1name, player2name, myGrid);
        winContains(winCheckAD, wx, wo, isWinner, whoWon, player1name, player2name, myGrid);
        if (count == 9 && !isWinner) {
            whoWon = "NO ONE won - It's a draw!";
            mListener.makeGameOverDialog(whoWon, player1name, player2name);
            isWinner = true;
        }
        return isWinner;
    }

    private static void winContains(String win, String wx, String wo, boolean isWinner, String whoWon,String player1name, String player2name, GridLayout myGrid ) {
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

            int count = 0;
            for (int i = 0; i < 9; i++) {
                //check if it's a draw
                SquareButton btn = (SquareButton) myGrid.getChildAt(i);
                if (!btn.isEnabled()) {
                    count++;
                }
            }

            if (winX || winO) {
              //  Toast.makeText(getActivity(), whoWon + "won", Toast.LENGTH_LONG).show(); //can't get any sort of context from here - if I want a toast, i have to create it in the GameBoardFrag

                isWinner = true;
            }
        }
    }
}
