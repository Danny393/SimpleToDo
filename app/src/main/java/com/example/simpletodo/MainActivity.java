package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //variables to use for logic
    List<String> itemList;
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemAdapter;

    static final String KEY_ITEM_TEXT = "item-text";
    static final String KEY_ITEM_POSITION = "item-position";
    final int EDIT_TEXT_CODE = 39;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //adding references to connect UI to code variables
        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        loadItems();

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
                //the data structure changes so we want to save
                saveItems();
            }
        };

        //the same as long click but for updating an item
        ItemsAdapter.ShortClickHandler tapListener = new ItemsAdapter.ShortClickHandler() {
            @Override
            public void handleShortClick(int position) {
                //We will start a new activity for the user to edit the item
                Intent editActivity = new Intent(MainActivity.this, EditActivity.class);
                //we want to send the activity some information to work with
                editActivity.putExtra(KEY_ITEM_TEXT, itemList.get(position));
                editActivity.putExtra(KEY_ITEM_POSITION, position);

                //start the activity and have it return information for the main activity to handle
                startActivityForResult(editActivity, EDIT_TEXT_CODE);
            }
        };

        //create the adapter for items
        itemAdapter = new ItemsAdapter(itemList, clickListener, tapListener);
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
                //the data structure changes so we want to save
                saveItems();
            }
        });
    }

    //Logic to handle return Intent result

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //make sure we are reacting the correct request and result code
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
            //get the data from the Intent returned
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);
            //update both the data structure and notify adapter
            itemList.set(position, itemText);
            itemAdapter.notifyItemChanged(position);
            //persist data
            saveItems();
            Toast.makeText(getApplicationContext(), "Item was updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Log.w("MainActivity","Unknown call to onActivityResult");
        }
    }

    //Logic for persistence

    //Function to load the file so that we can work with it
    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    //function to read items from the file, we only want to read from the file
    //when the app first start up
    private void loadItems(){
        try {
            itemList = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity","Error reading items", e);
            itemList = new ArrayList<>();
        }
    }

    //function to write items to the file, we only want to write to the file
    //when any change happens to the data structure that holds the items
    public void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), itemList);
        } catch (IOException e) {
            Log.e("MainActivity","Error writing items", e);
        }
    }
}
