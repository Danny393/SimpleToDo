package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //variables to use for logic
    List<String> itemList;
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //adding references to connect UI to code variables
        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        itemList = new ArrayList<>();

        //this is an object that allows the ViewHolder communicate to the Adapter
        //since it is created outside of the adapter it can pass info over
        ItemsAdapter.LongClickHandler clickListener = new ItemsAdapter.LongClickHandler() {
            @Override
            //so when an item is long clicked, the view holder calls this function
            //that is outside of the adapter to remove that item
            public void handleLongClick(int position) {
                //delete the item from the data structure
                itemList.remove(position);
                //notify the adapter to remove the item
                itemAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
            }
        };

        //create the adapter for items
        itemAdapter = new ItemsAdapter(itemList, clickListener);
        //let the recycler view use the adapter
        rvItems.setAdapter(itemAdapter);
        //tell the recycler view to display these items in a linear fashion
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        //On click listener that will handle some logic when the button is pressed
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //get input from text box
                String userInput = etItem.getText().toString();
                //add item to data structure
                itemList.add(userInput);
                //let the adapter know that new info has been added and needs to be updated
                itemAdapter.notifyItemInserted(itemList.size() - 1);
                //clear text from text box
                etItem.setText("");
                //display small pop up to confirm to th user that the item was added
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
