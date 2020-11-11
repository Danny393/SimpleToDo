package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Responsible for displaying data from the DataStructure into a row in the recycler view
public class ItemsAdapter extends  RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    List<String> itemList;

    public ItemsAdapter(List<String> list) {
        itemList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Use a layout inflater to inflate a view
        //This builds the UI using a layout file called simple_list_item_1, that looks like a list item
        //But a custom one can be made and used
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        //Then wrap in a view holder and return it
        return new ViewHolder(todoView);
    }

    //Responsible for adding(binding) data to a view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Grab item at position
        String item = itemList.get(position);

        //Bind the item to the view holder
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    //Container to provide ease of access to views that represent each row of the list
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        //Update the view inside the view holder with this item
        public void bind(String item) {
            tvItem.setText(item);
        }
    }
}
