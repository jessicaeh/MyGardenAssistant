package com.example.jessica.mygardeningassistant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewPlantScreen extends AppCompatActivity {

    DatabaseHelper db;
    private EditText name;
    private EditText loc;
    private EditText notes;
    private Button submit, btnCam;
    private ImageView mImageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plant_screen);

        name = (EditText) findViewById(R.id.editPlantName);
        loc = (EditText) findViewById(R.id.editPlantLocation);
        notes = (EditText) findViewById(R.id.editPlantNotes);
        submit = (Button) findViewById(R.id.submitButton);
        btnCam = (Button) findViewById(R.id.buttonCam);
        mImageView = (ImageView) findViewById(R.id.imageView2);
        db = new DatabaseHelper(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");

                String newEntryDate = df.format(c);
                String newEntryName = name.getText().toString();
                String newEntryLoc = loc.getText().toString();
                String newEntryNotes = notes.getText().toString();

                if (name.length() != 0 && loc.length() != 0){
//                    if (notes.length() == 0)
//                        newEntryNotes = " ";
                    AddData(newEntryName, newEntryDate, newEntryLoc, newEntryNotes);
                    name.setText("");
                    loc.setText("");
                    notes.setText("");
                } else {
                    toastMessage("You must put something in the date, name, and location fields!");
                }

                startActivity(new Intent(NewPlantScreen.this, MyGarden.class));
            }
        });

        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    public void AddData(String newEntryName, String newEntryDate, String newEntryLoc, String newEntryNotes){
        boolean insertData = db.addData(newEntryName, newEntryDate, newEntryLoc, newEntryNotes);

        if (insertData) {
//            toastMessage("Data Successfully Inserted");
        } else {
            toastMessage("Something went wrong. Make sure the name is not a duplicate!");
        }

    }

    private void toastMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }
}
