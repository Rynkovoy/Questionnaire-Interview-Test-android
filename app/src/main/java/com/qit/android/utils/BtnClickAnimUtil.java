package com.qit.android.utils;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.qit.R;

import java.util.List;

public class BtnClickAnimUtil {

    //method for white btns
    public BtnClickAnimUtil(View view, final Context context){
        final Button b = (Button) view;
        b.setTextColor(context.getResources().getColor(R.color.dark_gray));
        b.setBackgroundResource(R.drawable.custom_btn_click);
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                b.setTextColor(context.getResources().getColor(R.color.colorAuthText));
                b.setBackgroundResource(R.drawable.custom_btn);
            }
        };
        handler.postDelayed(runnable, 300);
    }

    //method for dark btns, i know that it`s horrible!!!
    public BtnClickAnimUtil(View view, final Context context, int x){
        final Button b = (Button) view;
        b.setTextColor(context.getResources().getColor(R.color.colorAuthText));
        b.setBackgroundResource(R.drawable.custom_btn_dark_click);
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                b.setTextColor(context.getResources().getColor(R.color.cardview_dark_background));
                b.setBackgroundResource(R.drawable.custom_btn_dark);
            }
        };
        handler.postDelayed(runnable, 300);
    }

    //method for pressed btns, i know that it`s horrible!!!
    public BtnClickAnimUtil(View view, final Context context, List<Button> btnsGroup){

        for (int x = 0; x < btnsGroup.size(); x++){
            btnsGroup.get(x).setTextColor(context.getResources().getColor(R.color.colorAuthText));
            btnsGroup.get(x).setBackgroundResource(R.drawable.custom_btn);
        }

        final Button b = (Button) view;
        b.setTextColor(context.getResources().getColor(R.color.dark_gray));
        b.setBackgroundResource(R.drawable.custom_btn_click);
    }

}
