package ra241_2015.pnrs1.rtrk.taskmanager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;



class CheckerThread extends Thread {

    private boolean mRun;
    private long PERIOD = 5000;
    private SimpleDateFormat format;
    private Context mContext;
    private NotificationManager mNotificationManager;
    private Notification.Builder mBuilder;

    CheckerThread(Context context){
        super();
        mContext=context;
        format = new SimpleDateFormat("hh:mm");
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new Notification.Builder(mContext)
                .setContentTitle("Task reminder")
                .setSmallIcon(R.drawable.red);
    }

    @Override
    public synchronized void start() {
        mRun = true;
        super.start();
    }

    synchronized void exit() {
        mRun = false;
    }

}