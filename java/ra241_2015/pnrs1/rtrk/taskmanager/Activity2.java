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

    Boolean greenFlag, redFlag, yellowFlag, dodajBtnEnabled = false;

    //DatePicker datePicker;

    /*Date picker*/
    protected DatePickerDialog.OnDateSetListener Date;
    protected DatePickerDialog DatePicker;

    /*Time picker*/
    protected TimePickerDialog.OnTimeSetListener Time;
    protected TimePickerDialog TimePicker;

    String datum, imeZadatkaText;

    protected String mDateString;
    protected String mTimeString;


    Calendar calendarCurrent, calendarSpecified;

    CheckBox podsjetnkikCheckBox;

    TextView mSetDate;
    TextView mSetTime;

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


        SetDate = (TextView) findViewById(R.id.datumTextView);
        SetTime = (TextView) findViewById(R.id.vrijemeTextView);


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
                        intent.putExtra("datum",datum);
                        intent.putExtra("imeZadatkaText", imeZadatkaText);

                        if (redFlag)
                            intent.putExtra("boja", R.drawable.red);
                        else if (greenFlag)
                            intent.putExtra("boja", R.drawable.green);
                        else
                            intent.putExtra("boja", R.drawable.yellow);

                        if(podsjetnkikCheckBox.isChecked())
                        {
                            intent.putExtra("checkBox", R.drawable.yellow_bell);
                        }

                        setResult(RESULT_OK, intent);
                        finish();
                        break;

                    case R.id.btnOtkazi:

                        Intent intent1 =new Intent();

                        if(getIntent().getExtras().getInt("flag") == 1)
                        {
                            setResult(RESULT_FIRST_USER, intent1);//result_obrisi
                        }
                        else
                        {
                            setResult(RESULT_CANCELED, intent1);
                        }

                        finish();
                        break;
                }
            }
        };


        btnGreen.setOnClickListener(ocl);
        btnRed.setOnClickListener(ocl);
        btnYellow.setOnClickListener(ocl);
        btnDodaj.setOnClickListener(ocl);
        btnOtkazi.setOnClickListener(ocl);

        btnDodaj.setText(getIntent().getExtras().getString("textNaBtnDodajSacuvaj"));
        btnOtkazi.setText(getIntent().getExtras().getString("textNaBtnOtkaziObrisi"));

        imeZadatka.addTextChangedListener(mTextWatcher);
        opisZadatka.addTextChangedListener(mTextWatcher);


        //datePicker.setMinDate(System.currentTimeMillis());


        calendarCurrent = Calendar.getInstance();
        calendarCurrent.setTimeInMillis(System.currentTimeMillis());

        calendarSpecified = Calendar.getInstance();
        calendarSpecified.setTimeInMillis(System.currentTimeMillis());

        datum = getString(R.string.danas);



        Date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendarCurrent.set(Calendar.YEAR, year);
                calendarCurrent.set(Calendar.MONTH, monthOfYear);
                calendarCurrent.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                int month = monthOfYear + 1;

                if(calendarSpecified.get(Calendar.YEAR) == year)
                {
                    if ((calendarCurrent.get(Calendar.DAY_OF_YEAR) - calendarSpecified.get(Calendar.DAY_OF_YEAR)) == 1)
                    {
                        datum = getString(R.string.sutra);
                    }
                    else if ((calendarCurrent.get(Calendar.DAY_OF_YEAR) - calendarSpecified.get(Calendar.DAY_OF_YEAR)) == 2)
                    {
                        datum = getString(R.string.prekosutra);
                    }
                    else if ((calendarCurrent.get(Calendar.DAY_OF_YEAR) - calendarSpecified.get(Calendar.DAY_OF_YEAR)) >= 3 && (calendarCurrent.get(Calendar.DAY_OF_YEAR) - calendarSpecified.get(Calendar.DAY_OF_YEAR))<7 )
                    {
                        switch (calendarCurrent.get(Calendar.DAY_OF_WEEK))
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
        };


        DatePicker = new DatePickerDialog(this, Date,
                calendarCurrent.get(Calendar.YEAR), calendarCurrent.get(Calendar.MONTH),
                calendarCurrent.get(Calendar.DAY_OF_MONTH));



        SetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mDatePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

                DatePicker.show();
            }
        });


        DatePicker.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                int mDayOfMonth = DatePicker.getDatePicker().getDayOfMonth();
                int mMonth = DatePicker.getDatePicker().getMonth()+1;
                int mYear = DatePicker.getDatePicker().getYear();
                String mDateYear = Integer.toString(mYear);
                String mDateMonth = Integer.toString(mMonth);
                String mDateDay = Integer.toString(mDayOfMonth);

                if(mDayOfMonth < 10)
                {
                    mDateDay = "0" + mDayOfMonth;
                }
                if(mMonth < 10)
                {
                    mDateMonth = "0" + mMonth;
                }
                mDateString = mDateDay + "-" + mDateMonth + "-" + mDateYear;

                mSetDate.setText(mDateString);
            }
        });




        Time = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                calendarCurrent.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarCurrent.set(Calendar.MINUTE, minute);
            }
        };

        TimePicker = new TimePickerDialog(this, Time,
                calendarCurrent.get(Calendar.HOUR_OF_DAY), calendarCurrent.get(Calendar.MINUTE),
                true);


        SetTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                TimePicker.show();
            }
        });



        TimePicker.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                int mHour = calendarCurrent.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendarCurrent.get(Calendar.MINUTE);

                String mHourS = Integer.toString(mHour);
                String mMinuteS = Integer.toString(mMinute);
                if(mHour < 10)
                {
                    mHourS = "0" + mHourS;
                }
                if(mMinute < 10)
                {
                    mMinuteS = "0" + mMinuteS;
                }
                mTimeString = mHourS + ":" + mMinuteS;
                /*Show picked time on button*/

                mSetTime.setText(mTimeString);
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
            checkFieldsForEmptyValues();
        }
    };


    void checkFieldsForEmptyValues(){

        String s1 = imeZadatka.getText().toString();
        String s2 = opisZadatka.getText().toString();

        if(s1.equals("") || s2.equals("")){

            btnDodaj.setEnabled(false);

        } else {
            if(dodajBtnEnabled)
                btnDodaj.setEnabled(true);
        }
    }

}//activity