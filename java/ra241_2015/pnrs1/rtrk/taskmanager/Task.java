package ra241_2015.pnrs1.rtrk.taskmanager;

import android.graphics.drawable.Drawable;



class Task {

    String mText;
    Drawable mImage;
    String mDate;
    Drawable mAlarm;

    Task(String text, Drawable drawable,String date, Drawable alarm) {
        mText = text;
        mImage = drawable;
        mDate = date;
        mAlarm = alarm;

    }

}
