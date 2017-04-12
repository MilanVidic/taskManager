package ra241_2015.pnrs1.rtrk.taskmanager;

class Task {

    String mText;
    int mImage;
    String mDate;
    int mAlarm;

    Task(String text, int drawable, String date, int alarm) {

        mText = text;
        mImage = drawable;
        mDate = date;
        mAlarm = alarm;

    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public int getmAlarm() {
        return mAlarm;
    }

    public void setmAlarm(int mAlarm) {
        this.mAlarm = mAlarm;
    }
}