package ra241_2015.pnrs1.rtrk.taskmanager;

class Task {

    String mText;
    int mImage;
    String mDate;
    int mAlarm;
    private boolean checked;

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    Task(String text, int drawable, String date, int alarm) {

        mText = text;
        mImage = drawable;
        mDate = date;
        mAlarm = alarm;

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


    public void setmDate(String mDate) {
        this.mDate = mDate;
    }


    public void setmAlarm(int mAlarm) {
        this.mAlarm = mAlarm;
    }
}