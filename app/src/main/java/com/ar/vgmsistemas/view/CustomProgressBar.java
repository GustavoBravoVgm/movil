package com.ar.vgmsistemas.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class CustomProgressBar extends ProgressBar {
    private int progressMark;
    Paint paint = new Paint();

    public CustomProgressBar(Context context) {
        super(context);

    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setMark(int mark) {
        progressMark = mark;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        paint.setColor(getResources().getColor(android.R.color.black));
        final float lineWidth = 3f;
        paint.setStrokeWidth(lineWidth);
        canvas.drawLine(getWidth() * progressMark / 100f, getHeight(), getWidth() * progressMark / 100f, 0, paint);

    }

}
