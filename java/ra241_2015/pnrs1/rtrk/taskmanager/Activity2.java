package ra241_2015.pnrs1.rtrk.taskmanager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class Activity2 extends AppCompatActivity {

    Button btnRed;
    Button btnGreen;
    Button btnYellow;

    Button btnDodaj;
    Button btnOtkazi;

    EditText imeZadatka;
    EditText opisZadatka;

    Boolean flag = false;

    Intent myIntent;

    DatePicker datePicker;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        btnGreen = (Button) findViewById(R.id.btnGreen);
        btnRed = (Button) findViewById(R.id.btnRed);
        btnYellow = (Button) findViewById(R.id.btnYellow);
        btnDodaj = (Button) findViewById(R.id.btnDodaj);
        btnOtkazi = (Button) findViewById(R.id.btnOtkazi);

        imeZadatka = (EditText) findViewById(R.id.imeZadatka);
        opisZadatka = (EditText) findViewById(R.id.opisZadatka);

        String data = getIntent().getExtras().getString("IntentDataDodaj");
        String data1 = getIntent().getExtras().getString("IntentDataOtkazi");


        btnDodaj.setText(data);
        btnOtkazi.setText(data1);

        btnOtkazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        myIntent = new Intent(Activity2.this, MainActivity.class);
                        startActivity(myIntent);

            }
        });

        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myIntent = new Intent(Activity2.this, MainActivity.class);
                startActivity(myIntent);

            }
        });


        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnGreen.setEnabled(false);
                btnGreen.setAlpha(.5f);
                btnRed.setEnabled(true);
                btnRed.setAlpha(1f);
                btnYellow.setEnabled(true);
                btnYellow.setAlpha(1f);
                flag=true;
                checkFieldsForEmptyValues();

            }
        });


        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnRed.setEnabled(false);
                btnRed.setAlpha(.5f);
                btnGreen.setEnabled(true);
                btnGreen.setAlpha(1f);
                btnYellow.setEnabled(true);
                btnYellow.setAlpha(1f);
                flag=true;
                checkFieldsForEmptyValues();

            }
        });


        btnYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnYellow.setEnabled(false);
                btnYellow.setAlpha(.5f);
                btnRed.setEnabled(true);
                btnRed.setAlpha(1f);
                btnGreen.setEnabled(true);
                btnGreen.setAlpha(1f);

                flag=true;
                checkFieldsForEmptyValues();
            }
        });




        checkFieldsForEmptyValues();


        datePicker = (DatePicker) findViewById(R.id.datePicker2);
        //datePicker.setMinDate(System.currentTimeMillis());




        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                String datum = "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth;
                Log.d("Date", datum);


            }
        });




        imeZadatka.addTextChangedListener(mTextWatcher);
        opisZadatka.addTextChangedListener(mTextWatcher);
    }


    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            checkFieldsForEmptyValues();
        }
    };


    void checkFieldsForEmptyValues(){

        String s1 = imeZadatka.getText().toString();
        String s2 = opisZadatka.getText().toString();

        if(s1.equals("") || s2.equals("")){

            btnDodaj.setEnabled(false);

        } else {
            if(flag)
                btnDodaj.setEnabled(true);
        }
    }

}
