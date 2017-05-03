package ra241_2015.pnrs1.rtrk.taskmanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;


public class PieChart extends View {
    private RectF rect = new RectF();
    private Paint paint = new Paint();
    private int percentage = 0, color;
    boolean flag = true;
    int max = 0;

    public void setMax(int max) {
        this.max = max;
    }

    public PieChart(Context context) {
        this(context, null);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void setPercentage() {


        if (this.percentage == max || max == 0) {
            flag = false;
            this.percentage--;
        }
        this.percentage++;
        invalidate();
    }

    public int getPercentage() {
        return percentage;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(color);
        canvas.drawArc(rect, 0, 3.6f * percentage, true, paint);

        paint.setColor(ContextCompat.getColor(getContext(), R.color.pozadinaPite));
        canvas.drawArc(rect, 3.6f * percentage, 360 - 3.6f * percentage, true, paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(80);
        canvas.drawText(String.valueOf(getPercentage()) + "%", getWidth() / 2 - 50, getHeight() / 2, paint);

        rect.set(0, 0, getWidth(), getHeight());

        if (flag) {
            setPercentage();
        }

    }
}