package com.example.jessica.mygardeningassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateTask extends AppCompatActivity {

    DBTaskHelper db;
    private EditText date;
    private EditText task;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        date = (EditText) findViewById(R.id.setDate);
        task = (EditText) findViewById(R.id.setTask);
        submit = (Button) findViewById(R.id.sendButton);
        db = new DBTaskHelper(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntryDate = date.getText().toString();
                String newEntryTask = task.getText().toString();

                if (task.length() != 0 && date.length() != 0){
                    AddData(newEntryTask, newEntryDate);
                    task.setText("");
                    date.setText("");
                } else {
                    toastMessage("You must put something in the date and task fields!");
                }

                startActivity(new Intent(CreateTask.this, CareCalendar.class));
            }
        });
    }

    public void AddData(String newEntryTask, String newEntryDate){
        boolean insertData = db.addData(newEntryTask, newEntryDate);

        if (insertData) {
//            toastMessage("Data Successfully Inserted");
        } else {
            toastMessage("Something went wrong. Make sure the name is not a duplicate!");
        }

    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
