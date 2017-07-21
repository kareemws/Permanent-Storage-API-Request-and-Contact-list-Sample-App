package com.kareemwaleed.arxicttask.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kareemwaleed.arxicttask.R;
import com.kareemwaleed.arxicttask.models.JsonListItem;

import java.util.ArrayList;

/**
 * Created by Kareem Waleed on 7/3/2017.
 */

public class JsonListAdapter extends ArrayAdapter {
    private ArrayList<JsonListItem> jsonListItems;
    public JsonListAdapter(Context context, ArrayList<JsonListItem> jsonListItems){
        super(context, 0, jsonListItems);
        this.jsonListItems = jsonListItems;
    }

    /**
     * Inflates the custom list item and initializes it
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        JsonListItem jsonListItem = jsonListItems.get(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.json_list_item, parent, false);
        TextView userID = (TextView) convertView.findViewById(R.id.user_id);
        userID.setText("User ID: " + jsonListItem.getUserID());
        TextView id = (TextView) convertView.findViewById(R.id.id);
        id.setText("ID: " + jsonListItem.getID());
        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText("Title: "+jsonListItem.getTitle());
        TextView body = (TextView) convertView.findViewById(R.id.body);
        body.setText("Body: "+jsonListItem.getBody());
        return convertView;
    }
}
