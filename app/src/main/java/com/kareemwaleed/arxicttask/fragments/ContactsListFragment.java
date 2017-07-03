package com.kareemwaleed.arxicttask.fragments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.kareemwaleed.arxicttask.R;
import com.kareemwaleed.arxicttask.activities.ContactDetails;
import com.kareemwaleed.arxicttask.adapters.ContactsListAdapter;
import com.kareemwaleed.arxicttask.models.ContactsListItem;

import java.util.ArrayList;

public class ContactsListFragment extends Fragment {
    private ListView listView;
    private ContactsListAdapter contactsListAdapter;
    private final int CONTACTS_PERMISSION_REQUEST = 0;

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
        int isPermissionGranted = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS);
        if (isPermissionGranted == PackageManager.PERMISSION_GRANTED) {
            prepareListView();
        } else {
            ActivityCompat.requestPermissions(getActivity()
                    , new String[]{Manifest.permission.READ_CONTACTS}, CONTACTS_PERMISSION_REQUEST);
        }
        return frameLayout;
    }

    private void prepareListView(){
        ArrayList<ContactsListItem> contactsList = getContacts();
        contactsListAdapter = new ContactsListAdapter(getContext(), contactsList);
        listView.setAdapter(contactsListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactsListItem tempContactsListItem = (ContactsListItem) contactsListAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), ContactDetails.class);
                intent.putExtra("name", tempContactsListItem.getName());
                intent.putExtra("phone", tempContactsListItem.getNumbers().get(0));
                intent.putExtra("email", tempContactsListItem.getEmail());
                startActivity(intent);
            }
        });
    }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CONTACTS_PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    prepareListView();
                } else {
                }
        }
    }
}
