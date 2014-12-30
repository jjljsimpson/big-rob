package android.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class VerticalSeekBar extends SeekBar  {

	SeekBar.OnSeekBarChangeListener changeListener;
	
    public VerticalSeekBar(Context context) {
        super(context);
        changeListener = null;
    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(Canvas c) {
        c.rotate(-90);
        c.translate(-getHeight(), 0);

        super.onDraw(c);
    }
    
    
    @Override
    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener listener)
    {
    	super.setOnSeekBarChangeListener(listener);
    	changeListener = listener;
    }
    
    
    @SuppressLint("ClickableViewAccessibility") 
    @Override    
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            	int val = getMax() - (int) (getMax() * event.getY() / getHeight());
                setProgress(val);
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                
                if(event.getAction() == MotionEvent.ACTION_UP && changeListener != null)
                	changeListener.onStopTrackingTouch(this);
                break;

            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
}