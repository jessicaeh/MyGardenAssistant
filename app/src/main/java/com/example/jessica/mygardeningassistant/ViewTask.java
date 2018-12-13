package com.example.jessica.mygardeningassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewTask extends AppCompatActivity {

    private static final String TAG = "ViewTask";

    DBTaskHelper db;
    private EditText date;
    private EditText task;
    private Button submit, delete;

    private String selectedTask, selectedDate;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        date = (EditText) findViewById(R.id.changeDate);
        task = (EditText) findViewById(R.id.changeTask);
        submit = (Button) findViewById(R.id.editButton);
        delete = (Button) findViewById(R.id.checkButton);
        db = new DBTaskHelper(this);

        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value
        selectedTask = receivedIntent.getStringExtra("task");
        selectedDate = receivedIntent.getStringExtra("date");

        //set the text to show the current selected name
        task.setText(selectedTask);
        date.setText(selectedDate);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEntryTask = task.getText().toString();
                String newEntryDate = date.getText().toString();

                if (task.length() != 0 && date.length() != 0){
                    db.updateData(newEntryTask, newEntryDate, selectedID);
                    task.setText("");
                    date.setText("");
                } else {
                    toastMessage("You must put something in the date and task fields!");
                }

                startActivity(new Intent(ViewTask.this, CareCalendar.class));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteData(selectedID);
//                toastMessage("removed from database");
                startActivity(new Intent(ViewTask.this, CareCalendar.class));
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
