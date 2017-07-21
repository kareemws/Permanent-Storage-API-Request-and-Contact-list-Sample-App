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
import com.kareemwaleed.arxicttask.models.ContactsListItem;

import java.util.ArrayList;

/**
 * Created by Kareem Waleed on 7/3/2017.
 */

public class ContactsListAdapter extends ArrayAdapter{
    ArrayList<ContactsListItem> contactsListItems;
    public ContactsListAdapter(Context context, ArrayList<ContactsListItem> contactsListItems){
        super(context, 0, contactsListItems);
        this.contactsListItems = contactsListItems;
    }

    /**
     * Inflates the custom list item and initializes it
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String contactName = contactsListItems.get(position).getName();
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contacts_list_item, parent, false);
        }
        TextView contactNameTextView = (TextView) convertView.findViewById(R.id.contacts_list_item_name_view);
        contactNameTextView.setText(contactName);
        return convertView;
    }


    /**
     * Returns a ContactListItem model instance based on the position argument
     */
    @Nullable
    @Override
    public Object getItem(int position) {
        return contactsListItems.get(position);
    }
}
