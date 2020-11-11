package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //variables to use for logic
    List<String> itemList;
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //adding references to connect UI to code variables
        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        itemList = new ArrayList<>();

        //create the adapter for items
        ItemsAdapter iAdapter = new ItemsAdapter(itemList);
        //let the recycler view use the adapter
        rvItems.setAdapter(iAdapter);
        //tell the recycler view to display these items in a linear fashion
        rvItems.setLayoutManager(new LinearLayoutManager(this));
    }
}
