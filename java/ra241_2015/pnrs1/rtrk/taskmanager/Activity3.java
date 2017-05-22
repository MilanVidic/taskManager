package ra241_2015.pnrs1.rtrk.taskmanager;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Activity3 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_3);


        PieChart PieChart1 = (PieChart) findViewById(R.id.crveni);
        PieChart PieChart2 = (PieChart) findViewById(R.id.zuti);
        PieChart PieChart3 = (PieChart) findViewById(R.id.zeleni);



                Log.d("debag", "ASSSS = " + getIntent().getExtras().getInt("redPostotak"));
                PieChart1.setMax(getIntent().getExtras().getInt("redPostotak"));
                PieChart1.setColor(Color.RED);


                PieChart2.setMax(getIntent().getExtras().getInt("yellowPostotak"));
                PieChart2.setColor(Color.YELLOW);

                PieChart3.setMax(getIntent().getExtras().getInt("greenPostotak"));
                PieChart3.setColor(Color.GREEN);
    }

}
