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

                PieChart1.setMax(85);
                PieChart1.getPaint().setColor(Color.RED);


                PieChart2.setMax(60);
                PieChart2.getPaint().setColor(Color.BLUE);

                PieChart3.setMax(30);
                PieChart3.getPaint().setColor(Color.GREEN);
    }

}
