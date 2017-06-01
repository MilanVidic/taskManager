package ra241_2015.pnrs1.rtrk.taskmanager;

class Task {

    String mName;
    private String mDescription;
    int mImage;
    String mDate;
    private String mTime;
    int mAlarm;
    private int mChecked;
    int mID;


    Task(int id, String imeZadatka, String opisZadatka, int drawable, String date, String time, int alarm, int checked) {

        mID = id;
        mName = imeZadatka;
        mDescription = opisZadatka;
        mImage = drawable;
        mDate = date;
        mTime = time;
        mAlarm = alarm;
        mChecked = checked;
    }


    public int getmID() {return mID;}
    public void setmID(int mID) {this.mID = mID;}

    String getmName() {
        return mName;
    }
    void setmName(String mName) {
        this.mName = mName;
    }

    String getmDescription() {
        return mDescription;
    }

    void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }


    int getmChecked() {
        return mChecked;
    }
    void setmChecked(int checked) {
        this.mChecked = checked;
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