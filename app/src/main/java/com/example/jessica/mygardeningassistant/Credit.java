package com.example.jessica.mygardeningassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Credit extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        text =  (TextView) findViewById(R.id.textView);

        String blurb = "Version 1 (WIP)\n\nCreated by Jessica Halbert\n\n\nImage Credit:\n\n" +
                "https://pngtree.com/free-icons/succulent plants\n\nfree succulent plants icons from pngtree.com";

        text.setText(blurb);
    }
}
