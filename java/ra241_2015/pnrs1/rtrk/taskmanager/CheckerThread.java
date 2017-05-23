package ra241_2015.pnrs1.rtrk.taskmanager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;



class CheckerThread extends Thread {

    private boolean mRun;
    private SimpleDateFormat format;
    private NotificationManager mNotificationManager;
    private Notification.Builder mBuilder;
    private Context mContext;

    CheckerThread(Context context){
        super();
        mContext=context;
        format = new SimpleDateFormat("hh:mm");
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new Notification.Builder(context)
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void run() {
        while(mRun){
            String msg = "15 минута до истека задатка: ";
            boolean notiHasItems=false;
            for (Task t:MainActivity.tasks) {
                Log.d("milan", "AAAA " + t.getmAlarm());
                if (t.getmDate().equals(mContext.getResources().getString(R.string.danas)) && !t.getChecked() && t.getmAlarm()!=0 ) {
                    Log.d("milan", "CCCCC");
                    Calendar current = Calendar.getInstance();
                    Calendar taskTime = Calendar.getInstance();
                    try {
                        taskTime.setTime(format.parse(t.getmTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if ( taskTime.get(Calendar.HOUR_OF_DAY) == current.get(Calendar.HOUR_OF_DAY) ) {
                        if(taskTime.get(Calendar.MINUTE)-current.get(Calendar.MINUTE)<=15 && taskTime.get(Calendar.MINUTE)-current.get(Calendar.MINUTE)>=0) {
                            if (notiHasItems)
                                msg += " , " + t.getmName();
                            else
                                msg += t.getmName();
                            notiHasItems = true;
                        }
                    }else if (taskTime.get(Calendar.HOUR_OF_DAY) - current.get(Calendar.HOUR_OF_DAY) == 1) {
                        if(taskTime.get(Calendar.MINUTE)+60-current.get(Calendar.MINUTE)<=15 && taskTime.get(Calendar.MINUTE)+60-current.get(Calendar.MINUTE)>=0){
                            if (notiHasItems)
                                msg += " , " + t.getmName();
                            else
                                msg += t.getmName();
                            notiHasItems = true;
                        }
                    }
                }
            }
            if(notiHasItems) {
                mBuilder.setContentTitle(msg);
                mNotificationManager.notify(0, mBuilder.build());
            }else{
                mNotificationManager.cancel(0);
            }
            try {
                long PERIOD = 2000;
                sleep(PERIOD);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}