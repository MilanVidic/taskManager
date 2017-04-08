package ra241_2015.pnrs1.rtrk.taskmanager;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    Button noviZadatak;
    Button statistika;
    Intent myIntent;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noviZadatak = (Button) findViewById(R.id.noviZadatak);
        statistika = (Button) findViewById(R.id.statistika);



        noviZadatak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myIntent = new Intent(MainActivity.this, Activity2.class);
                myIntent.putExtra("IntentDataDodaj", getString(R.string.dodaj));
                myIntent.putExtra("IntentDataOtkazi", getString(R.string.otkazi));
                startActivity(myIntent);
            }
        });


        statistika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myIntent = new Intent(MainActivity.this, Activity3.class);
                startActivity(myIntent);
            }
        });


        customAdapter adapter = new customAdapter(this);

       ;

        adapter.addTask(new Task(getString(R.string.dodaj), ContextCompat.getDrawable(this, R.drawable.red), getString(R.string.datum), ContextCompat.getDrawable(this,android.R.drawable.btn_star)));
        //adapter.addTask(new Task(getString(R.string.dodaj), ContextCompat.getDrawable(this, R.drawable.green), getString(R.string.datum)));
        //adapter.addTask(new Task(getString(R.string.dodaj), ContextCompat.getDrawable(this, R.drawable.yellow), getString(R.string.datum)));
        //adapter.addTask(new Task(getString(R.string.dodaj), ContextCompat.getDrawable(this, R.drawable.green), getString(R.string.datum)));
        //adapter.addTask(new Task(getString(R.string.otkazi), ContextCompat.getDrawable(this, R.drawable.red), getString(R.string.datum)));



        ListView list = (ListView) findViewById(R.id.mainListView);
        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                // TODO Auto-generated method stub

                myIntent = new Intent(MainActivity.this, Activity2.class);
                myIntent.putExtra("IntentDataDodaj", getString(R.string.sacuvaj));
                myIntent.putExtra("IntentDataOtkazi", getString(R.string.obrisi));
                startActivity(myIntent);


                Log.v("long clicked","pos: " + pos);

                return true;
            }
        });



    }//onCreate

}//mainActivity
