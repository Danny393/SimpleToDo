package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText etUpdate;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getSupportActionBar().setTitle("Edit Item");
        etUpdate = findViewById(R.id.etUpdate);
        btnUpdate = findViewById(R.id.btnUpdate);

        //fill the text box with what was on the item that they clicked
        etUpdate.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));
        //button that sets up an update return value
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //prepare result Intent
                Intent result = new Intent();
                //prepare values to send back to the main activity
                result.putExtra(MainActivity.KEY_ITEM_TEXT, etUpdate.getText().toString());
                result.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
                //set the result code for main activity to be able to catch it
                setResult(RESULT_OK, result);
                //terminates the current activity
                finish();
            }
        });

    }
}