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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erde_vacation_planner.R;
import com.example.erde_vacation_planner.dao.Repository;
import com.example.erde_vacation_planner.entities.Excursion;
import com.example.erde_vacation_planner.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {

    String name;
    String housing;
    String startDate;
    String endDate;
    int vacationID;
    Vacation currentVacation;
    int numVacations;

    EditText editName;
    EditText editHousing;
    Button editStartDate;
    Button editEndDate;

    DatePickerDialog.OnDateSetListener myDate;
    DatePickerDialog.OnDateSetListener myDate2;
    final Calendar myCalendar = Calendar.getInstance();

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);

        editName = findViewById(R.id.vacationname);
        editHousing = findViewById(R.id.housing);
        editStartDate = findViewById(R.id.startdate);
        editEndDate = findViewById(R.id.enddate);

        name = getIntent().getStringExtra("name");
        housing = getIntent().getStringExtra("housing");
        startDate = getIntent().getStringExtra("startdate");
        endDate = getIntent().getStringExtra("enddate");
        vacationID = getIntent().getIntExtra("id", -1);

        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        editName.setText(name);
        editHousing.setText(housing);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);

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
                new DatePickerDialog(VacationDetails.this, myDate, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editEndDate.getText().toString();
                try {
                    myCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, myDate2, myCalendar.get(Calendar.YEAR),
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

        myDate2 = new DatePickerDialog.OnDateSetListener() {
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
                editEndDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationid", vacationID);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Excursion> filteredExcursions = new ArrayList<>();

        for (Excursion e : repository.getmAllExcursions()) {
            if (e.getVacationID() == vacationID) {
                filteredExcursions.add(e);
            }
        }
        excursionAdapter.setExcursions(filteredExcursions);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = repository.getAssociatedExcursions(vacationID);
        excursionAdapter.setExcursions(filteredExcursions);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.vacationsave) {
            Vacation vacation;
            String dateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            if (vacationID == -1) {
                if (repository.getmAllVacations().isEmpty()) {
                    vacationID = 1;
                } else {
                    vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationID() + 1;
                }
                vacation = new Vacation(vacationID, editName.getText().toString(), editHousing.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                try {
                    Date vacationStartDate = sdf.parse(vacation.getStartDate());
                    Date vacationEndDate = sdf.parse(vacation.getEndDate());
                    if (vacationEndDate.before(vacationStartDate)) {
                        Toast.makeText(VacationDetails.this, "Vacation end date must be AFTER the vacation start date.", Toast.LENGTH_LONG).show();
                        return false;
                    } else {
                        repository.insert(vacation);
                        Toast.makeText(VacationDetails.this, vacation.getVacationName() + " was saved.", Toast.LENGTH_LONG).show();
                        this.finish();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                vacation = new Vacation(vacationID, editName.getText().toString(), editHousing.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                try {
                    Date vacationStartDate = sdf.parse(vacation.getStartDate());
                    Date vacationEndDate = sdf.parse(vacation.getEndDate());
                    if (vacationEndDate.before(vacationStartDate)) {
                        Toast.makeText(VacationDetails.this, "Vacation end date must be AFTER the vacation start date.", Toast.LENGTH_LONG).show();
                        return false;
                    } else {
                        repository.insert(vacation);
                        Toast.makeText(VacationDetails.this, vacation.getVacationName() + " was saved.", Toast.LENGTH_LONG).show();
                        this.finish();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        if (menuItem.getItemId() == R.id.vacationdelete) {
            for (Vacation vacation : repository.getmAllVacations()) {
                if (vacation.getVacationID() == vacationID) {
                    currentVacation = vacation;
                }
            }
            numVacations = 0;
            for (Excursion excursion : repository.getmAllExcursions()) {
                if (excursion.getVacationID() == vacationID) {
                    ++numVacations;
                }
            }
            if (vacationID == -1) {
                Toast.makeText(VacationDetails.this, "Vacation does not exist.", Toast.LENGTH_LONG).show();
            }
            if (numVacations == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted.", Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                Toast.makeText(VacationDetails.this, "Can't delete a vacation with excursions.", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if (menuItem.getItemId() == R.id.sharevacation) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Housing: " + editHousing.getText().toString() + System.lineSeparator() + "Start Date: " + editStartDate.getText().toString() + System.lineSeparator() + "End Date: " + editEndDate.getText().toString());
            sendIntent.putExtra(Intent.EXTRA_TITLE, editName.getText().toString());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        if (menuItem.getItemId() == R.id.notifyonstart) {
            Intent sendStartIntent = new Intent(VacationDetails.this, MyReceiver.class);
            sendStartIntent.putExtra("Alert", editName.getText().toString() + " starts today!");
            String startDateFromScreen = editStartDate.getText().toString();
            String dateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            Date vacationStartDate = null;
            try {
                vacationStartDate = sdf.parse(startDateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long triggerStartDate = vacationStartDate.getTime();

            PendingIntent senderVacationStart = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, sendStartIntent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(alarmManager.RTC_WAKEUP, triggerStartDate, senderVacationStart);
            return true;
        }
        if (menuItem.getItemId() == R.id.notifyonend) {
            Intent sendEndIntent = new Intent(VacationDetails.this, MyReceiver.class);
            sendEndIntent.putExtra("Alert", editName.getText().toString() + " ends today!");
            String endDateFromScreen = editEndDate.getText().toString();
            String dateFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            Date vacationEndDate = null;
            try {
                vacationEndDate = sdf.parse(endDateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long triggerEndDate = vacationEndDate.getTime();

            PendingIntent senderVacationEnd = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, sendEndIntent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(alarmManager.RTC_WAKEUP, triggerEndDate, senderVacationEnd);
            return true;
        }
        return true;
    }
}