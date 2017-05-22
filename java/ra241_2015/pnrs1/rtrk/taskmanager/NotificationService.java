package ra241_2015.pnrs1.rtrk.taskmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NotificationService extends Service {

    private CheckerThread mCheckerThread;
    private ServiceNotifier mServiceNotifier;

    @Override
    public void onCreate() {
        mCheckerThread = new CheckerThread(this);
        mCheckerThread.start();
        mServiceNotifier = new ServiceNotifier(this);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        mCheckerThread.exit();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mServiceNotifier;
    }
}