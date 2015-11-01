package com.example.android.tictactoe;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageButton;

import java.lang.*;

/**
 * Created by cayte on 10/21/15.
 */
public class SquareButton extends ImageButton {

    int screenWidthDp;
    int screenHeightDp;

    public SquareButton(Context context) {
        super(context);
    }

    public SquareButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getScreenWidth(), getScreenHeight()); //WHY DID IMPORTING JAVA.LANG NOT WORK??? //so it works in both landscape and portrait orientations
        setMeasuredDimension(dp2px(size / 3), dp2px(size / 3)); //later include padding / margins //needs to take in PIXELS, NOT DPs
    }

    private int getScreenWidth(){
        Configuration configuration = this.getResources().getConfiguration();
        return screenWidthDp = configuration.screenWidthDp; //The current width of the available screen space, in dp units, corresponding to screen width resource qualifier.
    }

    private int getScreenHeight(){
        Configuration configuration = this.getResources().getConfiguration();
        return screenHeightDp = configuration.screenHeightDp; //The current Height of the available screen space, in dp units, corresponding to screen width resource qualifier.
    }

    public int dp2px (int dp){
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int px = Math.round (dp * (dm.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
