package ra241_2015.pnrs1.rtrk.taskmanager;

class Task {

    String mText;
    int mImage;
    String mDate;
    int mAlarm;

    Task(String text, int drawable,String date, int alarm) {
        mText = text;
        mImage = drawable;
        mDate = date;
        mAlarm = alarm;

    }

}