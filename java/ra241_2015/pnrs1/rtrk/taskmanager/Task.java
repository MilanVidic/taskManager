package ra241_2015.pnrs1.rtrk.taskmanager;

class Task {

    String mText;
    int mImage;
    String mDate;
    String mTime;
    int mAlarm;
    private boolean checked;

    boolean getChecked() {
        return checked;
    }

    void setChecked(boolean checked) {
        this.checked = checked;
    }


    public String getmDate() {
        return mDate;
    }

    public String getmText() {
        return mText;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public int getmAlarm() {
        return mAlarm;
    }

    Task(String text, int drawable, String date, String time, int alarm) {

        mText = text;
        mImage = drawable;
        mDate = date;
        mTime = time;
        mAlarm = alarm;

    }


    void setmText(String mText) {
        this.mText = mText;
    }

    int getmImage() {
        return mImage;
    }

    void setmImage(int mImage) {
        this.mImage = mImage;
    }


    void setmDate(String mDate) {
        this.mDate = mDate;
    }


    void setmAlarm(int mAlarm) {
        this.mAlarm = mAlarm;
    }
}