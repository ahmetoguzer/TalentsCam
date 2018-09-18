package com.technoface.app.talentscam.Utils;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Ahmet Oguzer on 31.10.2017.
 */

public class ViewOnTouch {

    public static void onTouchEffect(View view){
        view.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    v.setAlpha(0.5f);
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    v.setAlpha(1);
                }
                return false;
            }
        });
    }
}
