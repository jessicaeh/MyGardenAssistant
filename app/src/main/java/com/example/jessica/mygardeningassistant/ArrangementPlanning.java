package com.example.jessica.mygardeningassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ArrangementPlanning extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrangement_planning);

        text =  (TextView) findViewById(R.id.textView3);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("I'm looking for...");
        categories.add("Tall plants");
        categories.add("Short plants");
        categories.add("Wide plants");
        categories.add("Thin plants");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String tall = "Sunflowers\nGladioli\nAgeratum\nTulips\nAloe\nSnake Plant\nMadagascar Ocotillo";
        String shorts = "Sweetherat Hoya\nPanda Plant\nBarrel Cactus\nJade";
        String wide = "Agave\nFern\nHens and Chicks\nZebra Plant\nPeace Lily\nParlor Palm";
        String thin = "Burro's Tail\nDragon Tree\nZZ Plant";

        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        if (item != "Select a lesson:") {
            switch (item) {
                case "Tall plants":
                    text.setText(tall);
                    break;
                case "Short plants":
                    text.setText(shorts);
                    break;
                case "Wide plants":
                    text.setText(wide);
                    break;
                case "Thin plants":
                    text.setText(thin);
                    break;
            }
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
