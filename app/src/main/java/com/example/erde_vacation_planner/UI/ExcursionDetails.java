package com.example.erde_vacation_planner.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.erde_vacation_planner.R;
import com.example.erde_vacation_planner.dao.Repository;
import com.example.erde_vacation_planner.entities.Excursion;
import com.example.erde_vacation_planner.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {
    String name;
    String startDate;
    int vacationID;
    int excursionID;
    Excursion currentExcursion;

    EditText editName;
    Button editStartDate;

    DatePickerDialog.OnDateSetListener myDate;
    final Calendar myCalendar = Calendar.getInstance();

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);
        repository = new Repository(getApplication());

        editName = findViewById(R.id.excursionname);
        editStartDate = findViewById(R.id.startdate);

        name = getIntent().getStringExtra("name");
        startDate = getIntent().getStringExtra("startdate");
        excursionID = getIntent().getIntExtra("id", -1);
        vacationID = getIntent().getIntExtra("vacationid", -1);

        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        editName.setText(name);
        editStartDate.setText(startDate);

        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editStartDate.getText().toString();
                try {
                    myCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(ExcursionDetails.this, myDate, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });

        myDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
                editStartDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (menuItem.getItemId() == R.id.excursionsave) {
            repository = new Repository(getApplication());
            Excursion excursion;
            String dateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            Date vacationStartDate = new Date();
            Date vacationEndDate = new Date();
            if (excursionID == -1) {
                if (repository.getmAllExcursions().isEmpty()) {
                    excursionID = 1;
                } else {
                    excursionID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionID() + 1;
                }
                excursion = new Excursion(excursionID, editName.getText().toString(), vacationID, editStartDate.getText().toString());
                try {
                    for (Vacation vacation : repository.getmAllVacations()) {
                        if (vacation.getVacationID() == vacationID) {
                            vacationStartDate = sdf.parse(vacation.getStartDate());
                            vacationEndDate = sdf.parse(vacation.getEndDate());
                        }
                    }
                    Date excursionStartDate = sdf.parse(excursion.getStartDate());
                    if ((excursionStartDate.before(vacationStartDate)) || (excursionStartDate.after(vacationEndDate))) {
                        Toast.makeText(ExcursionDetails.this, "Excursion must be during the vacation.", Toast.LENGTH_LONG).show();
                        return false;
                    } else {
                        repository.insert(excursion);
                        Toast.makeText(ExcursionDetails.this, excursion.getExcursionName() + " was saved.", Toast.LENGTH_LONG).show();
                        this.finish();
                        return true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                repository.insert(excursion);
                this.finish();
            } else {
                excursion = new Excursion(excursionID, editName.getText().toString(), vacationID, editStartDate.getText().toString());
                try {
                    for (Vacation vacation : repository.getmAllVacations()) {
                        if (vacation.getVacationID() == vacationID) {
                            vacationStartDate = sdf.parse(vacation.getStartDate());
                            vacationEndDate = sdf.parse(vacation.getEndDate());
                        }
                    }
                    Date excursionStartDate = sdf.parse(excursion.getStartDate());
                    if ((excursionStartDate.before(vacationStartDate)) || (excursionStartDate.after(vacationEndDate))) {
                        Toast.makeText(ExcursionDetails.this, "Excursion must be during the vacation.", Toast.LENGTH_LONG).show();
                        return false;
                    } else {
                        repository.insert(excursion);
                        Toast.makeText(ExcursionDetails.this, excursion.getExcursionName() + "Excursion was saved.", Toast.LENGTH_LONG).show();
                        this.finish();
                        return true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        if (menuItem.getItemId() == R.id.excursiondelete) {
            for (Excursion excursion : repository.getmAllExcursions()) {
                if (excursion.getExcursionID() == excursionID) {
                    currentExcursion = excursion;
                }
            }
            if (excursionID == -1) {
                Toast.makeText(ExcursionDetails.this, "Excursion does not exist.", Toast.LENGTH_LONG).show();
            } else {
                repository.delete(currentExcursion);
                Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionName() + " was deleted.", Toast.LENGTH_LONG).show();
                this.finish();
            }
        }
        if (menuItem.getItemId() == R.id.shareexcursion) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Name: " + editName.getText().toString() + System.lineSeparator() + "Start Date: " + editStartDate.getText().toString() + System.lineSeparator());
            sendIntent.putExtra(Intent.EXTRA_TITLE, editName.getText().toString());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        if (menuItem.getItemId() == R.id.notify) {
            Intent sendExcursionIntent = new Intent(ExcursionDetails.this, MyReceiver.class);
            sendExcursionIntent.putExtra("Alert", editName.getText().toString() + " is today! Have fun!");
            String startDateFromScreen = editStartDate.getText().toString();
            String dateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            Date excursionDate = null;
            try {
                excursionDate = sdf.parse(startDateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long triggerExcursion = excursionDate.getTime();

            PendingIntent senderExcursion = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, sendExcursionIntent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(alarmManager.RTC_WAKEUP, triggerExcursion, senderExcursion);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}