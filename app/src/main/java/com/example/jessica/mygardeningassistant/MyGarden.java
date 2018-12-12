package com.example.jessica.mygardeningassistant;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyGarden extends AppCompatActivity {
    private static final String TAG = "MyGarden";

    DatabaseHelper db;
    private ListView plantListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_garden);
        plantListView = (ListView) findViewById(R.id.plantListView);
        db = new DatabaseHelper(this);

        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = db.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(1) + " (added " + data.getString(2) + ")");
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        plantListView.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        plantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String hold = adapterView.getItemAtPosition(i).toString();
                String name = hold.substring(0, hold.indexOf(" ("));
                Log.d(TAG, "onItemClick: You Clicked on " + name + ".");

                Cursor data = db.getItemID(name); //get the id associated with that name
                int itemID = -1;
                String itemDate = null;
                String itemLoc = null;
                String itemNotes = null;

                while(data.moveToNext()){
                    itemID = data.getInt(0);
                    itemDate = data.getString(2);
                    itemLoc = data.getString(3);
                    itemNotes = data.getString(4);
                    Log.d(TAG, "onItemClick: 1 " + data.getInt(0) + " 2 " + data.getString(1) +
                            " 3 " + data.getString(2) + " 4 " + data.getString(3) + " 5 " + data.getString(4));
                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID + " date " + itemDate + " loc " + itemLoc + " note " + itemNotes);
                    Intent editScreenIntent = new Intent(MyGarden.this, ViewPlantScreen.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name", name);
                    editScreenIntent.putExtra("date", itemDate);
                    editScreenIntent.putExtra("loc", itemLoc);
                    editScreenIntent.putExtra("notes", itemNotes);
                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public void goNewPlantScreen (View view) {
        Intent intent = new Intent(this, NewPlantScreen.class);
        startActivity(intent);
    }
}
