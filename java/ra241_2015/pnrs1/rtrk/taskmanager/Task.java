package ra241_2015.pnrs1.rtrk.taskmanager;

class Task {

    String mName;



    String mDescription;
    int mImage;
    String mDate;
    private String mTime;
    int mAlarm;
    private boolean checked;


    Task(String imeZadatka, String opisZadatka, int drawable, String date, String time, int alarm) {

        mName = imeZadatka;
        mDescription = opisZadatka;
        mImage = drawable;
        mDate = date;
        mTime = time;
        mAlarm = alarm;

    }

    String getmName() {
        return mName;
    }
    void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }


    boolean getChecked() {
        return checked;
    }
    void setChecked(boolean checked) {
        this.checked = checked;
    }

    String getmDate() {
        return mDate;
    }
    void setmDate(String mDate) {
        this.mDate = mDate;
    }

    String getmTime() {
        return mTime;
    }
    void setmTime(String mTime) {
        this.mTime = mTime;
    }

    int getmAlarm() {return mAlarm;}
    void setmAlarm(int mAlarm) {
        this.mAlarm = mAlarm;
    }

    int getmImage() {
        return mImage;
    }
    void setmImage(int mImage) {
        this.mImage = mImage;
    }





}