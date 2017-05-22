package ra241_2015.pnrs1.rtrk.taskmanager;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ServiceConnection {


    Button noviZadatak, statistika;
    Intent myIntent;
    customAdapter adapter;
    ListView list;
    final int SHORT_CLICK_NOVI_ZADATAK = 1;
    final int LONG_CLICK_NOVI_ZADATAK = 2;
    public static String TEXT_NA_BTN_DODAJ_SACUVAJ = "textNaBtnDodajSacuvaj";
    public static String TEXT_NA_BTN_OTKAZI_OBRISI = "textNaBtnOtkaziObrisi";
    public static String IME_ZADATKA = "imeZadatkaText";
    public static String BOJA = "boja";
    public static String DATUM = "datum";
    public static String CHECKBOX = "checkBox";
    public static String FLAG_ZA_BTN_SACUVAJ = "checkBox";
    public static int SACUVAJ = 5;
    int position;
    public static ArrayList<Task> tasks;
    private AidlInterface mBinderInterface;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noviZadatak = (Button) findViewById(R.id.noviZadatak);
        statistika = (Button) findViewById(R.id.statistika);
        list = (ListView) findViewById(R.id.mainListView);

        adapter = new customAdapter(this);

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.noviZadatak:

                        myIntent = new Intent(MainActivity.this, Activity2.class);
                        myIntent.putExtra(TEXT_NA_BTN_DODAJ_SACUVAJ, getString(R.string.dodaj));
                        myIntent.putExtra(TEXT_NA_BTN_OTKAZI_OBRISI, getString(R.string.otkazi));
                        startActivityForResult(myIntent, SHORT_CLICK_NOVI_ZADATAK);
                        break;

                    case R.id.statistika:

                        myIntent = new Intent(MainActivity.this, Activity3.class);
                        izracunajStatistiku(myIntent);
                        startActivity(myIntent);

                        break;
                }
            }
        };

        noviZadatak.setOnClickListener(ocl);
        statistika.setOnClickListener(ocl);

        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                myIntent.putExtra(TEXT_NA_BTN_DODAJ_SACUVAJ, getString(R.string.sacuvaj));
                myIntent.putExtra(TEXT_NA_BTN_OTKAZI_OBRISI, getString(R.string.obrisi));
                myIntent.putExtra(FLAG_ZA_BTN_SACUVAJ, 1); //ako je dodajBtnEnabled 1, btn otkazi postaje obrisi
                position = pos;
                startActivityForResult(myIntent, LONG_CLICK_NOVI_ZADATAK);

                return true;
            }
        });

        tasks = adapter.getmTasks();


        ServiceConnection mServiceConnection = this;
        Intent serviceIntent = new Intent(this, NotificationService.class);
        bindService(serviceIntent, mServiceConnection, BIND_AUTO_CREATE);


    }//onCreate


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == SHORT_CLICK_NOVI_ZADATAK && resultCode == RESULT_OK) {
            if (intent != null) {
                adapter.addTask(new Task(intent.getStringExtra(IME_ZADATKA), intent.getExtras().getInt(BOJA), intent.getStringExtra(DATUM), intent.getExtras().getInt(CHECKBOX)));
                try {
                    mBinderInterface.notifyAdd();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == LONG_CLICK_NOVI_ZADATAK && resultCode == RESULT_FIRST_USER)//result_obrisi
        {
            adapter.removeTask(position);
            try {
                mBinderInterface.notifyDelete();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if (requestCode == LONG_CLICK_NOVI_ZADATAK && resultCode == SACUVAJ) {
            adapter.editTask(intent.getStringExtra(IME_ZADATKA), intent.getExtras().getInt(BOJA), intent.getStringExtra(DATUM), intent.getExtras().getInt(CHECKBOX), position);
            try {
                mBinderInterface.notifyEdit();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

    }

    public void izracunajStatistiku(Intent intent) {
        int redBr = 0;
        int yellowBr = 0;
        int greenBr = 0;
        int redBrChecked = 0;
        int greenBrChecked = 0;
        int yellowBrChecked = 0;

        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getmTasks().get(i).getmImage() == R.drawable.red) {
                redBr++;
                if (adapter.getmTasks().get(i).getChecked()) {
                    redBrChecked++;
                }
            } else if (adapter.getmTasks().get(i).getmImage() == R.drawable.green) {
                greenBr++;
                if (adapter.getmTasks().get(i).getChecked()) {
                    greenBrChecked++;
                }
            } else {
                yellowBr++;
                if (adapter.getmTasks().get(i).getChecked()) {
                    yellowBrChecked++;
                }
            }
        }
        if (redBr == 0) {
            redBrChecked = 0;
        } else
            redBrChecked = redBrChecked * 100 / redBr;
        if (greenBr == 0) {
            greenBrChecked = 0;
        } else
            greenBrChecked = greenBrChecked * 100 / greenBr;
        if (yellowBr == 0) {
            yellowBrChecked = 0;
        }else
            yellowBrChecked = yellowBrChecked * 100 / yellowBr;

        intent.putExtra("redPostotak", redBrChecked);
        intent.putExtra("greenPostotak", greenBrChecked);
        intent.putExtra("yellowPostotak", yellowBrChecked);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mBinderInterface = AidlInterface.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}//mainActivity