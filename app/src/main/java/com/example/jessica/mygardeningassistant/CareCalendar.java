package com.example.jessica.mygardeningassistant;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CareCalendar extends AppCompatActivity {
    private static final String TAG = "CareCalendar";

    DBTaskHelper db;
    private ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_calendar);
        taskListView = (ListView) findViewById(R.id.eventListView);
        db = new DBTaskHelper(this);

        populateListView();
    }

    private void populateListView() {
//        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = db.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(2) + ": " + data.getString(1));
        }

        if (data != null && !data.isClosed()) {
            data.close();
        }

        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        taskListView.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String hold = adapterView.getItemAtPosition(i).toString();
                String date = hold.substring(0, hold.indexOf(":"));
                String task = hold.substring(hold.indexOf(": ") + 2);
//                Log.d(TAG, "onItemClick: You Clicked on " + date + " with task " + task + ".");

                Cursor data = db.getItemID(task, date); //get the id associated with that name
                int itemID = -1;

                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
//                    Log.d(TAG, "onItemClick: The ID is: " + itemID + " date " + date + " task " + task  + ".");
                    Intent editScreenIntent = new Intent(CareCalendar.this, ViewTask.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("task", task);
                    editScreenIntent.putExtra("date", date);
                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that name");
                }

                if (data != null && !data.isClosed()) {
                    data.close();
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

    public void goCreateEvent (View view) {
        Intent intent = new Intent(this, CreateTask.class);
        startActivity(intent);
    }
}
