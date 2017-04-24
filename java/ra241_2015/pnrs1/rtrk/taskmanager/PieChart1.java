package ra241_2015.pnrs1.rtrk.taskmanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;


public class PieChart1 extends View
{
    private RectF rect = new RectF();
    private Paint paint = new Paint();
    private int percentage = 0;
    boolean flag=true;



    public PieChart1(Context context)
    {
        this(context, null);
    }

    public PieChart1(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void setPercentage()
    {
        this.percentage = this.getPercentage() + 1;

        int max = 97;
        if (this.percentage == max) {
            flag = false;
        }
        invalidate();
    }

    public int getPercentage()
    {
        return percentage;
    }



    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        paint.setColor(Color.RED);
        canvas.drawArc(rect, 0, 3.6f * percentage, true, paint);

        paint.setColor(ContextCompat.getColor(getContext(), R.color.pozadinaPite));
        canvas.drawArc(rect, 3.6f * percentage , 360 - 3.6f * percentage, true, paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText(String.valueOf(getPercentage()) + "%",getWidth()/2 - 75,getHeight()/2,paint);

        rect.set(0,0, getWidth(), getHeight());

        if (flag) {
            setPercentage();
        }

    }
}