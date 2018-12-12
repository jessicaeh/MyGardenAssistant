package com.example.jessica.mygardeningassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewPlantScreen extends AppCompatActivity {

    private static final String TAG = "ViewPlantActivity";

    DatabaseHelper db;
    private EditText name;
    private EditText date;
    private EditText loc;
    private EditText notes;
    private Button submit, delete;

    private String selectedName, selectedDate, selectedLoc, selectedNotes;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plant_screen);

        name = (EditText) findViewById(R.id.changePlantName);
        date = (EditText) findViewById(R.id.changePlantDate);
        loc = (EditText) findViewById(R.id.changePlantLocation);
        notes = (EditText) findViewById(R.id.changePlantNotes);
        submit = (Button) findViewById(R.id.changeButton);
        delete = (Button) findViewById(R.id.deleteButton);
        db = new DatabaseHelper(this);

        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value
        selectedName = receivedIntent.getStringExtra("name");
        selectedDate = receivedIntent.getStringExtra("date");
        selectedLoc = receivedIntent.getStringExtra("loc");
        selectedNotes = receivedIntent.getStringExtra("notes");

        //set the text to show the current selected name
        name.setText(selectedName);
        date.setText(selectedDate);
        loc.setText(selectedLoc);
        notes.setText(selectedNotes);
        Log.d(TAG, "Name " + selectedName + " date " + selectedDate + " loc " + selectedLoc + " note " + selectedNotes);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEntryName = name.getText().toString();
                String newEntryDate = date.getText().toString();
                String newEntryLoc = loc.getText().toString();
                String newEntryNotes = notes.getText().toString();

                if (name.length() != 0 && loc.length() != 0){
                    db.updateData(newEntryName, newEntryDate, newEntryLoc, newEntryNotes, selectedID);
                    name.setText("");
                    date.setText("");
                    loc.setText("");
                    notes.setText("");
                } else {
                    toastMessage("You must put something in the date, name, and location fields!!");
                }

                startActivity(new Intent(ViewPlantScreen.this, MyGarden.class));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteData(selectedID);
                toastMessage("removed from database");
                startActivity(new Intent(ViewPlantScreen.this, MyGarden.class));
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
