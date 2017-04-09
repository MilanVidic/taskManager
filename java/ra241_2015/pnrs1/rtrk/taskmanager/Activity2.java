package ra241_2015.pnrs1.rtrk.taskmanager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

public class Activity2 extends AppCompatActivity {

    Button btnRed;
    Button btnGreen;
    Button btnYellow;

    Button btnDodaj;
    Button btnOtkazi;

    EditText imeZadatka;
    EditText opisZadatka;

    Boolean flag = false;
    Boolean greenFlag, redFlag, yellowFlag;


    DatePicker datePicker;

    String datum;
    String imeZadatkaText;
    Calendar calendar, calendar2;
    Date today;

    CheckBox podsjetnkikCheckBox;

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

        podsjetnkikCheckBox = (CheckBox) findViewById(R.id.checkBox2);

        String data = getIntent().getExtras().getString("IntentDataDodaj");
        String data1 = getIntent().getExtras().getString("IntentDataOtkazi");



        btnDodaj.setText(data);
        btnOtkazi.setText(data1);




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
                redFlag = false;
                greenFlag = true;
                yellowFlag = false;

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
                redFlag = true;
                greenFlag = false;
                yellowFlag = false;
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
                yellowFlag = true;
                redFlag = false;
                greenFlag = false;
                flag=true;
                checkFieldsForEmptyValues();
            }
        });




        checkFieldsForEmptyValues();


        datePicker = (DatePicker) findViewById(R.id.datePicker2);
       // datePicker.setMinDate(System.currentTimeMillis());




        calendar = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar2.setTimeInMillis(System.currentTimeMillis());

        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);

// and get that as a Date
        today = calendar2.getTime();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);


                Date dateSpecified = calendar.getTime();

               /* Log.d("Milan", "DATUM " + calendar2.get(Calendar.MONTH));
                Log.d("Milan", "DATUM " + calendar2.get(Calendar.MONTH)+2);
                Log.d("Milan", "DATUM " + calendar2.get(Calendar.DAY_OF_MONTH)+5+1);
                Log.d("Milan", "DATUM " + month);
                Log.d("Milan", "DATUM " + dayOfMonth);
                Log.d("Milan", "DATUM " + year);*/
                if (dateSpecified.equals(today)) {
                    datum = "Danas";
                }
                else if (dateSpecified.compareTo(today) == 1) {
                    datum = "Sutra";
                } else if (calendar2.get(Calendar.MONTH) == month && calendar2.get(Calendar.YEAR)==year && calendar2.get(Calendar.DAY_OF_MONTH)+2>=dayOfMonth && calendar2.get(Calendar.DAY_OF_MONTH)+7<dayOfMonth) {

                    switch (dayOfMonth) {
                        case Calendar.SUNDAY:
                            datum = "Nedelja";

                        case Calendar.MONDAY:
                            datum = "ponedeljak";

                        case Calendar.TUESDAY:
                            datum = "utorak";
                        case Calendar.WEDNESDAY:
                            datum = "srijeda";
                        case Calendar.THURSDAY:
                            datum = "cetvrtak";
                        case Calendar.FRIDAY:
                            datum = "petak";
                        case Calendar.SATURDAY:
                            datum = "subota";
                    }


                }else if (dateSpecified.compareTo(today) == 3) {
                    datum = "BBBB";
                }



            }
        });















        btnDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent();
                intent.putExtra("datum",datum);
                intent.putExtra("imeZadatkaText",imeZadatkaText);
                if (redFlag)
                    intent.putExtra("boja", R.drawable.red);
                else if (greenFlag)
                    intent.putExtra("boja", R.drawable.green);
                else
                    intent.putExtra("boja", R.drawable.yellow);

                if(podsjetnkikCheckBox.isChecked())
                {
                    intent.putExtra("checkBox", android.R.drawable.ic_popup_reminder);
                }

                setResult(RESULT_OK, intent);
                finish();

            }
        });


        btnOtkazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                int flag = getIntent().getExtras().getInt("flag");
                if(flag==1)
                {
                    setResult(RESULT_FIRST_USER, intent);
                }
                else
                {
                    setResult(RESULT_CANCELED, intent);
                }
                finish();
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

            imeZadatkaText = imeZadatka.getText().toString();
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
