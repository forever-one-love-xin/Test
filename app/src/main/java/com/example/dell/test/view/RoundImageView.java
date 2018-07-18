package com.example.dell.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/*
 * 设置圆形imageview，让图片为圆角
 *
 * */
public class RoundImageView extends android.support.v7.widget.AppCompatImageView {

    float width, height;
    float radius;

    public RoundImageView(Context context) {
        this (context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType (View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout (changed, left, top, right, bottom);
        width = getWidth ();
        height = getHeight ();
        radius = width / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (width > radius && height > radius) {
            Path path = new Path ();
            path.moveTo (radius, 0);
            path.lineTo (width - radius, 0);
            path.quadTo (width, 0, width, radius);
            path.lineTo (width, height - radius);
            path.quadTo (width, height, width - radius, height);
            path.lineTo (radius, height);
            path.quadTo (0, height, 0, height - radius);
            path.lineTo (0, radius);
            path.quadTo (0, 0, radius, 0);
            canvas.clipPath (path);
        }
        super.onDraw (canvas);
    }

}