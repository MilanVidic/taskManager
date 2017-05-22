package ra241_2015.pnrs1.rtrk.taskmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;


class ServiceNotifier extends AidlInterface.Stub{

    private NotificationManager mNotificationManager;
    private Notification.Builder mBuilder;
    private Context mContext;

    ServiceNotifier(Context context){
        mContext = context;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void notifyEdit() throws RemoteException {
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new Notification.Builder(mContext)
                .setContentTitle("Задатак измјењен!")
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm);
        mNotificationManager.notify(1, mBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void notifyAdd() throws RemoteException {
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new Notification.Builder(mContext)
                .setContentTitle("Задатак додан!")
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm);
        mNotificationManager.notify(1, mBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void notifyDelete() throws RemoteException {
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new Notification.Builder(mContext)
                .setContentTitle("Задатак обрисан!")
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm);
        mNotificationManager.notify(1, mBuilder.build());
    }
}