package ra241_2015.pnrs1.rtrk.taskmanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class Activity2 extends AppCompatActivity {



    Button btnRed, btnGreen, btnYellow, btnDodaj, btnOtkazi;

    EditText imeZadatka, opisZadatka;

    Boolean greenFlag, redFlag, yellowFlag, dodajBtnEnabled = false, vrijemeFlag = false, datumFlag=false;


    DatePickerDialog DatePicker;
    TimePickerDialog TimePicker;

    String datum, imeZadatkaText, opisZadatkaText;

    protected String DateString;
    protected String TimeString;


    Calendar calendarCurrent, calendarSpecified;

    CheckBox podsjetnkikCheckBox;
    CheckBox checkedCheckBox;

    TextView Datum;
    TextView Vrijeme;

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
        checkedCheckBox = (CheckBox) findViewById(R.id.checkBox);


        Datum = (TextView) findViewById(R.id.datumTextView);
        Vrijeme = (TextView) findViewById(R.id.vrijemeTextView);


        checkFieldsForEmptyValues();


        View.OnClickListener ocl = new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                switch (v.getId())
                {
                    case R.id.btnGreen:

                        btnGreen.setEnabled(false);
                        btnGreen.setAlpha(.25f);
                        btnRed.setEnabled(true);
                        btnRed.setAlpha(1f);
                        btnYellow.setEnabled(true);
                        btnYellow.setAlpha(1f);
                        dodajBtnEnabled =true;
                        redFlag = false;
                        greenFlag = true;
                        yellowFlag = false;

                        checkFieldsForEmptyValues();

                        break;

                    case R.id.btnRed:

                        btnRed.setEnabled(false);
                        btnRed.setAlpha(.25f);
                        btnGreen.setEnabled(true);
                        btnGreen.setAlpha(1f);
                        btnYellow.setEnabled(true);
                        btnYellow.setAlpha(1f);
                        dodajBtnEnabled =true;
                        redFlag = true;
                        greenFlag = false;
                        yellowFlag = false;

                        checkFieldsForEmptyValues();

                        break;

                    case R.id.btnYellow:

                        btnYellow.setEnabled(false);
                        btnYellow.setAlpha(.25f);
                        btnRed.setEnabled(true);
                        btnRed.setAlpha(1f);
                        btnGreen.setEnabled(true);
                        btnGreen.setAlpha(1f);
                        yellowFlag = true;
                        redFlag = false;
                        greenFlag = false;
                        dodajBtnEnabled =true;

                        checkFieldsForEmptyValues();

                        break;

                    case R.id.btnDodaj:

                        Intent intent = new Intent();
                        intent.putExtra(MainActivity.DATUM,datum);
                        intent.putExtra(MainActivity.SAT,TimeString);
                        intent.putExtra(MainActivity.IME_ZADATKA, imeZadatkaText);
                        intent.putExtra(MainActivity.OPIS_ZADATKA, opisZadatkaText);



                        if (redFlag)
                            intent.putExtra(MainActivity.BOJA, R.drawable.red);
                        else if (greenFlag)
                            intent.putExtra(MainActivity.BOJA, R.drawable.green);
                        else
                            intent.putExtra(MainActivity.BOJA, R.drawable.yellow);

                        if(podsjetnkikCheckBox.isChecked())
                        {
                            intent.putExtra(MainActivity.CHECKBOX_ALARM, R.drawable.yellow_bell);
                        }


                       if(getIntent().getExtras().getInt(MainActivity.FLAG_ZA_BTN_SACUVAJ) == 1)
                       {
                           setResult(MainActivity.SACUVAJ, intent);

                       }
                       else
                       {
                           setResult(RESULT_OK, intent);
                       }


                        finish();
                        break;

                    case R.id.btnOtkazi:

                        Intent intent1 = new Intent();

                        if(getIntent().getExtras().getInt(MainActivity.FLAG_ZA_BTN_SACUVAJ) == 1)
                        {
                            setResult(RESULT_FIRST_USER, intent1);//result_obrisi
                        }
                        else
                        {
                            setResult(RESULT_CANCELED, intent1);
                        }

                        finish();
                        break;

                    case R.id.datumTextView:

                       // DatePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
                        DatePicker.show();
                        datumFlag = true;
                        break;
                    case R.id.vrijemeTextView:

                        TimePicker.show();
                        vrijemeFlag = true;
                        break;
                }
            }
        };








        btnGreen.setOnClickListener(ocl);
        btnRed.setOnClickListener(ocl);
        btnYellow.setOnClickListener(ocl);
        btnDodaj.setOnClickListener(ocl);
        btnOtkazi.setOnClickListener(ocl);
        Datum.setOnClickListener(ocl);
        Vrijeme.setOnClickListener(ocl);

        btnDodaj.setText(getIntent().getExtras().getString(MainActivity.TEXT_NA_BTN_DODAJ_SACUVAJ));
        btnOtkazi.setText(getIntent().getExtras().getString(MainActivity.TEXT_NA_BTN_OTKAZI_OBRISI));


        if(getIntent().getExtras().getInt(MainActivity.FLAG_ZA_BTN_SACUVAJ) == 1) {

            Task task = MainActivity.mTaskDataBase.readTask(String.valueOf(MainActivity.position));
            datum = task.getmDate();
            TimeString = task.getmTime();
            imeZadatkaText = task.getmName();
            opisZadatkaText = task.getmDescription();

            imeZadatka.setText(getIntent().getExtras().getString(MainActivity.IME_ZADATKA));
            opisZadatka.setText(getIntent().getExtras().getString(MainActivity.OPIS_ZADATKA));
            Datum.setText(getIntent().getExtras().getString(MainActivity.DATUM));
            Vrijeme.setText(getIntent().getExtras().getString(MainActivity.SAT));

            if (getIntent().getExtras().getInt(MainActivity.BOJA) == R.drawable.red)
                btnRed.performClick();
            else if (getIntent().getExtras().getInt(MainActivity.BOJA) == R.drawable.green)
                btnGreen.performClick();
            else
                btnYellow.performClick();
            if ((getIntent().getExtras().getInt(MainActivity.CHECKBOX_ALARM) == R.drawable.yellow_bell)) {
                Log.d("gorane", "AAAAAAAAAAAAAA");
                podsjetnkikCheckBox.setChecked(true);
            }

        }

        imeZadatka.addTextChangedListener(mTextWatcher);
        opisZadatka.addTextChangedListener(mTextWatcher);



        calendarCurrent = Calendar.getInstance();
        calendarCurrent.setTimeInMillis(System.currentTimeMillis());

        calendarSpecified = Calendar.getInstance();
        calendarSpecified.setTimeInMillis(System.currentTimeMillis());




        DatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {

                calendarSpecified.set(Calendar.YEAR, year);
                calendarSpecified.set(Calendar.MONTH, monthOfYear);
                calendarSpecified.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                int month = monthOfYear + 1;

                if(calendarSpecified.get(Calendar.YEAR) == year)
                {
                    if ((calendarSpecified.get(Calendar.DAY_OF_YEAR) - calendarCurrent.get(Calendar.DAY_OF_YEAR)) == 0)
                    {
                        datum = getString(R.string.danas);
                    }
                    else if ((calendarSpecified.get(Calendar.DAY_OF_YEAR) - calendarCurrent.get(Calendar.DAY_OF_YEAR)) == 1)
                    {
                        datum = getString(R.string.sutra);
                    }
                    else if ((calendarSpecified.get(Calendar.DAY_OF_YEAR) - calendarCurrent.get(Calendar.DAY_OF_YEAR)) == 2)
                    {
                        datum = getString(R.string.prekosutra);
                    }
                    else if ((calendarSpecified.get(Calendar.DAY_OF_YEAR) - calendarCurrent.get(Calendar.DAY_OF_YEAR)) >= 3 && (calendarSpecified.get(Calendar.DAY_OF_YEAR) - calendarCurrent.get(Calendar.DAY_OF_YEAR))<=7 )
                    {
                        switch (calendarSpecified.get(Calendar.DAY_OF_WEEK))
                        {
                            case(Calendar.MONDAY):
                                datum = getString(R.string.ponedeljak);
                                break;
                            case(Calendar.TUESDAY):
                                datum = getString(R.string.utorak);
                                break;
                            case(Calendar.WEDNESDAY):
                                datum = getString(R.string.srijeda);
                                break;
                            case(Calendar.THURSDAY):
                                datum = getString(R.string.cetvrtak);
                                break;
                            case(Calendar.FRIDAY):
                                datum = getString(R.string.petak);
                                break;
                            case(Calendar.SATURDAY):
                                datum = getString(R.string.subota);
                                break;
                            case(Calendar.SUNDAY):
                                datum = getString(R.string.nedelja);
                                break;
                        }
                    }
                    else
                    {

                        datum = dayOfMonth + "/" + month + "/" + year;
                    }

                }
                else
                {
                    datum = dayOfMonth + "/" + month + "/" + year;
                }
            }

        }, calendarCurrent.get(Calendar.YEAR), calendarCurrent.get(Calendar.MONTH), calendarCurrent.get(Calendar.DAY_OF_MONTH));



        DatePicker.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                int DayOfMonth = DatePicker.getDatePicker().getDayOfMonth();
                int Month = DatePicker.getDatePicker().getMonth()+1;
                int Year = DatePicker.getDatePicker().getYear();

                String dan = Integer.toString(DayOfMonth);
                String mjesec = Integer.toString(Month);
                String godina = Integer.toString(Year);

                if(DayOfMonth < 10)
                {
                    dan = "0" + dan;
                }
                if(Month < 10)
                {
                    mjesec = "0" + mjesec;
                }
                DateString = dan + "/" + mjesec + "/" + godina;

                Datum.setText(DateString);
            }
        });




        TimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                calendarCurrent.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarCurrent.set(Calendar.MINUTE, minute);
            }
        }, calendarCurrent.get(Calendar.HOUR_OF_DAY), calendarCurrent.get(Calendar.MINUTE), true);


        TimePicker.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                int Hour = calendarCurrent.get(Calendar.HOUR_OF_DAY);
                int Minute = calendarCurrent.get(Calendar.MINUTE);

                String sat = Integer.toString(Hour);
                String minut = Integer.toString(Minute);

                if(Hour < 10)
                {
                    sat = "0" + sat;
                }
                if(Minute < 10)
                {
                    minut = "0" + minut;
                }
                TimeString = sat + ":" + minut;

                Vrijeme.setText(TimeString);
            }
        });



    }//onCreate


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
            opisZadatkaText = opisZadatka.getText().toString();
            checkFieldsForEmptyValues();
        }
    };


    void checkFieldsForEmptyValues(){

        String s1 = imeZadatka.getText().toString();
        String s2 = opisZadatka.getText().toString();
        String s3 = Datum.getText().toString();
        String s4 = Vrijeme.getText().toString();

        if(s1.equals("") || s2.equals("") || s3.equals("") || s4.equals("")){

            btnDodaj.setEnabled(false);

        } else {
            if(dodajBtnEnabled)
                btnDodaj.setEnabled(true);
        }
    }

}//activity