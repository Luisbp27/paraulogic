package com.example.paraulogic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    /**
     * Constructor de la classe
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


        TextView solutions = (TextView) findViewById(R.id.palabras);
        solutions.setText(Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT));
    }
}