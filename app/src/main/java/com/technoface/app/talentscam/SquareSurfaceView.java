package com.technoface.app.talentscam;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by Ahmet Oguzer on 8.11.2017.
 */

public class SquareSurfaceView extends SurfaceView {
    public SquareSurfaceView(Context context) {
        super(context);
    }

    public SquareSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }
}
