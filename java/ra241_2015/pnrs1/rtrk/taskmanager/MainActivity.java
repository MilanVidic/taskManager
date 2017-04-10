package ra241_2015.pnrs1.rtrk.taskmanager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {


    Button noviZadatak;
    Button statistika;
    Intent myIntent;
    customAdapter adapter;
    static final int PICK_CONTACT_REQUEST = 1;
    int position;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noviZadatak = (Button) findViewById(R.id.noviZadatak);
        statistika = (Button) findViewById(R.id.statistika);

        adapter = new customAdapter(this);

        noviZadatak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myIntent = new Intent(MainActivity.this, Activity2.class);
                myIntent.putExtra("IntentDataDodaj", getString(R.string.dodaj));
                myIntent.putExtra("IntentDataOtkazi", getString(R.string.otkazi));
                startActivityForResult(myIntent, PICK_CONTACT_REQUEST);
            }
        });


        statistika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myIntent = new Intent(MainActivity.this, Activity3.class);
                startActivity(myIntent);
            }
        });



        ListView list = (ListView) findViewById(R.id.mainListView);
        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {


                myIntent = new Intent(MainActivity.this, Activity2.class);
                myIntent.putExtra("IntentDataDodaj", getString(R.string.sacuvaj));
                myIntent.putExtra("IntentDataOtkazi", getString(R.string.obrisi));
                myIntent.putExtra("flag",1);
                position = pos;
                startActivityForResult(myIntent, 2);


                return true;
            }
        });



    }//onCreate


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == PICK_CONTACT_REQUEST && resultCode == RESULT_OK) {

            if (intent != null) {
                adapter.addTask(new Task(intent.getStringExtra("imeZadatkaText"), intent.getExtras().getInt("boja"),intent.getStringExtra("datum"), intent.getExtras().getInt("checkBox")));
                adapter.notifyDataSetChanged();
            }

        }
        else if (requestCode == 2 && resultCode == RESULT_FIRST_USER)
        {
            adapter.removeTask(position);

        }

    }



}//mainActivity