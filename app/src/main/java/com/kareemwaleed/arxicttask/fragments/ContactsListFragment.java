package com.kareemwaleed.arxicttask.fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.kareemwaleed.arxicttask.R;
import com.kareemwaleed.arxicttask.activities.ContactDetailsActivity;
import com.kareemwaleed.arxicttask.adapters.ContactsListAdapter;
import com.kareemwaleed.arxicttask.models.ContactsListItem;

import java.util.ArrayList;

public class ContactsListFragment extends Fragment {
    private ListView listView;
    private ContactsListAdapter contactsListAdapter;
    private boolean permissionGranted;

    public ContactsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_contacts_list, container, false);
        listView = (ListView) frameLayout.findViewById(R.id.contacts_list);
        //Gets the read contact permission from the parent activity (TabsActviity)
        permissionGranted = getArguments().getBoolean("permission");
        if(permissionGranted)
            prepareListView();
        return frameLayout;
    }

    private void prepareListView(){
        ArrayList<ContactsListItem> contactsList = getContacts();
        contactsListAdapter = new ContactsListAdapter(getContext(), contactsList);
        listView.setAdapter(contactsListAdapter);
        /**
         * Handles list item click by directing the user to a new activity representing contact details
         * Passes to the new activity the details that will be shown and retrieved here
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactsListItem tempContactsListItem = (ContactsListItem) contactsListAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), ContactDetailsActivity.class);
                intent.putExtra("name", tempContactsListItem.getName());
                intent.putExtra("phone", tempContactsListItem.getNumbers().get(0));
                intent.putExtra("email", tempContactsListItem.getEmail());
                startActivity(intent);
            }
        });
    }

    /**
     * Retrieves the id, display name, phone number and email information of all the contacts
     */
    public ArrayList<ContactsListItem> getContacts() {
        ArrayList<ContactsListItem> tempContactsArrayList = new ArrayList<>();
        ContactsListItem tempContactsListItem = null;
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                tempContactsListItem = new ContactsListItem();
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                tempContactsListItem.setName(name);

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        tempContactsListItem.addNumber(phoneNo);
                    }
                    pCur.close();
                }
                Cursor emailCursore = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null
                        , ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                while (emailCursore.moveToNext()){
                   String email = emailCursore.getString(
                            emailCursore.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    tempContactsListItem.setEmail(email);
                }
                tempContactsArrayList.add(tempContactsListItem);
            }
        }
        return tempContactsArrayList;
    }
}
