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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ServiceConnection {


    Button noviZadatak, statistika;
    Intent intentZaActivity2, intentZaActivity3;
    customAdapter adapter;
    ListView list;
    final int SHORT_CLICK_NOVI_ZADATAK = 1;
    final int LONG_CLICK_NOVI_ZADATAK = 2;
    public static String TEXT_NA_BTN_DODAJ_SACUVAJ = "textNaBtnDodajSacuvaj";
    public static String TEXT_NA_BTN_OTKAZI_OBRISI = "textNaBtnOtkaziObrisi";
    public static String IME_ZADATKA = "imeZadatkaText";
    public static String OPIS_ZADATKA = "opisZadatkaText";
    public static String BOJA = "boja";
    public static String DATUM = "datum";
    public static String SAT = "sat";
    public static String CHECKBOX_ALARM = "checkBoxAlarm";
    public static String FLAG_ZA_BTN_SACUVAJ = "checkBox";
    public static int SACUVAJ = 5;
    static int position;
    public static ArrayList<Task> tasks;
    private AidlInterface mBinderInterface;
    static TaskDataBase mTaskDataBase;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noviZadatak = (Button) findViewById(R.id.noviZadatak);
        statistika = (Button) findViewById(R.id.statistika);
        list = (ListView) findViewById(R.id.mainListView);

        adapter = new customAdapter(this);
        mTaskDataBase = new TaskDataBase(this);

        intentZaActivity2 = new Intent(MainActivity.this, Activity2.class);
        intentZaActivity3 = new Intent(MainActivity.this, Activity3.class);

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.noviZadatak:


                        intentZaActivity2.putExtra(TEXT_NA_BTN_DODAJ_SACUVAJ, getString(R.string.dodaj));
                        intentZaActivity2.putExtra(TEXT_NA_BTN_OTKAZI_OBRISI, getString(R.string.otkazi));
                        intentZaActivity2.putExtra(FLAG_ZA_BTN_SACUVAJ, 0);//zasto  mi je ulazilo u onaj long click???
                        startActivityForResult(intentZaActivity2, SHORT_CLICK_NOVI_ZADATAK);
                        break;

                    case R.id.statistika:

                        if(izracunajStatistiku(intentZaActivity3))
                            startActivity(intentZaActivity3);

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

                intentZaActivity2.putExtra(TEXT_NA_BTN_DODAJ_SACUVAJ, getString(R.string.sacuvaj));
                intentZaActivity2.putExtra(TEXT_NA_BTN_OTKAZI_OBRISI, getString(R.string.obrisi));
                intentZaActivity2.putExtra(FLAG_ZA_BTN_SACUVAJ, 1); //ako je dodajBtnEnabled 1, btn otkazi postaje obrisi


                Task task = mTaskDataBase.readTask(String.valueOf(pos));
                /*moglo u bundle*/
                intentZaActivity2.putExtra(DATUM,task.getmDate());
                intentZaActivity2.putExtra(SAT,task.getmTime());
                intentZaActivity2.putExtra(IME_ZADATKA, task.getmName());
                intentZaActivity2.putExtra(OPIS_ZADATKA, task.getmDescription());
                intentZaActivity2.putExtra(BOJA, task.getmImage());
                intentZaActivity2.putExtra(CHECKBOX_ALARM, task.getmAlarm());


                position = pos;
                startActivityForResult(intentZaActivity2, LONG_CLICK_NOVI_ZADATAK);

                return true;
            }
        });

        tasks = adapter.getmTasks();


        ServiceConnection mServiceConnection = this;
        Intent serviceIntent = new Intent(this, NotificationService.class);
        bindService(serviceIntent, mServiceConnection, BIND_AUTO_CREATE);


    }//onCreate


    @Override
    protected void onResume() {
        super.onResume();

        Task[] tasks = mTaskDataBase.readTasks();
        adapter.update(tasks);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mBinderInterface != null) {
            unbindService(this);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == SHORT_CLICK_NOVI_ZADATAK && resultCode == RESULT_OK) {                   //DODAJ
            if (intent != null) {
                Log.d("milan", "getCountaaaaaa= " + adapter.getCount());
                Task task = new Task(adapter.getCount(),intent.getStringExtra(IME_ZADATKA), intent.getStringExtra(OPIS_ZADATKA),
                                     intent.getExtras().getInt(BOJA), intent.getStringExtra(DATUM), intent.getStringExtra(SAT),
                                     intent.getExtras().getInt(CHECKBOX_ALARM),0);

                mTaskDataBase.insert(task);
                Task[] tasks = mTaskDataBase.readTasks();
                adapter.update(tasks);

                Log.d("milan", "position dodaj = " + String.valueOf(position));
                Log.d("milan", "getCountaa = " + adapter.getCount());


                try {
                    mBinderInterface.notifyAdd();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == LONG_CLICK_NOVI_ZADATAK && resultCode == RESULT_FIRST_USER)        //OBRISI
        {


            mTaskDataBase.deleteTask(String.valueOf(position));

            if(adapter.getCount()>1)
                izmjeniID(position);

            Task[] tasks = mTaskDataBase.readTasks();
            adapter.update(tasks);

            Log.d("milan", "position obrisi = " + String.valueOf(position));
            Log.d("milan", "getCountbb = " + adapter.getCount());



            try {
                mBinderInterface.notifyDelete();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if (requestCode == LONG_CLICK_NOVI_ZADATAK && resultCode == SACUVAJ) {                //EDIT
            adapter.editTask(intent.getStringExtra(IME_ZADATKA), intent.getStringExtra(OPIS_ZADATKA),
                             intent.getExtras().getInt(BOJA), intent.getStringExtra(DATUM), intent.getStringExtra(SAT),
                             intent.getExtras().getInt(CHECKBOX_ALARM), position,  0);
            Task task = (Task) adapter.getItem(position);
            mTaskDataBase.updateTask(task, String.valueOf(position));


            Task[] tasks = mTaskDataBase.readTasks();
            adapter.update(tasks);

            Log.d("milan", "getCountcc = " + adapter.getCount());
            Log.d("milan", "position edit = " + String.valueOf(position));
            try {
                mBinderInterface.notifyEdit();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

    }

    public boolean izracunajStatistiku(Intent intent) {
        int redBr = 0;
        int yellowBr = 0;
        int greenBr = 0;
        int redBrChecked = 0;
        int greenBrChecked = 0;
        int yellowBrChecked = 0;

        Task[] tasks = mTaskDataBase.readTasks();
        if (adapter.getCount()==0)
        {
            Toast.makeText(getApplicationContext(), "Нема задатака",
                    Toast.LENGTH_LONG).show();
            return false;
        }
       //
       //
        for (Task task : tasks) {
            Log.d("mChecked","mChecked123 = " + task.getmID());
            if (task.getmImage() == R.drawable.red) {
                redBr++;
                if (task.getmChecked()==1) {
                    redBrChecked++;
                }
            } else if (task.getmImage() == R.drawable.green) {
                greenBr++;
                if (task.getmChecked()==1) {
                    greenBrChecked++;
                }
            } else {
                yellowBr++;
                if (task.getmChecked()==1) {
                    yellowBrChecked++;
                }
            }
        }
        Log.d("redbr", "yellowBr = " + yellowBr);
        Log.d("redbr", "yellowBrChecked = " + yellowBrChecked);
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

        return true;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mBinderInterface = AidlInterface.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    void izmjeniID (int position)
    {
        Task[] tasks = mTaskDataBase.readTasks();
        for (Task task : tasks) {
            if (task.getmID()> position)
            {
                task.setmID(task.getmID()-1);
                mTaskDataBase.updateTask(task, String.valueOf(task.getmID()+1));
            }
        }

    }
}//mainActivity