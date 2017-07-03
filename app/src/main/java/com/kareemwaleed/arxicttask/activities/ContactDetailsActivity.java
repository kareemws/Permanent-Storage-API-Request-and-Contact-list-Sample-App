package com.kareemwaleed.arxicttask.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kareemwaleed.arxicttask.R;
import com.kareemwaleed.arxicttask.models.ContactsListItem;

public class ContactDetailsActivity extends AppCompatActivity {
    private RelativeLayout phoneNumberLayout;
    private RelativeLayout emailLayout;
    private TextView contactName;
    private TextView phoneNumber;
    private TextView email;
    private ContactsListItem contactDetails;
    private ImageButton callButton;
    private final int PHONE_CALL_PERMISSION_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        initViewVars();
        contactDetails = new ContactsListItem();
        contactDetails.setName(getIntent().getStringExtra("name"));
        contactDetails.setEmail(getIntent().getStringExtra("email"));
        contactDetails.addNumber(getIntent().getStringExtra("phone"));

        contactName.setText(contactDetails.getName());

        if (contactDetails.getNumbers() == null)
            phoneNumberLayout.setVisibility(View.GONE);
        else {
            phoneNumber.setText(contactDetails.getNumbers().get(0));
            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + contactDetails.getNumbers().get(0)));
                    if (ContextCompat.checkSelfPermission(ContactDetailsActivity.this, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ContactDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}
                                , PHONE_CALL_PERMISSION_REQUEST);
                    } else
                        startActivity(intent);
                }
            });
        }

        if (contactDetails.getEmail() == null)
            emailLayout.setVisibility(View.GONE);
        else
            email.setText(contactDetails.getEmail());
    }

    private void initViewVars() {
        phoneNumberLayout = (RelativeLayout) findViewById(R.id.phone_number_layout);
        emailLayout = (RelativeLayout) findViewById(R.id.email_layout);
        contactName = (TextView) findViewById(R.id.contact_name);
        phoneNumber = (TextView) findViewById(R.id.phone_number);
        email = (TextView) findViewById(R.id.email);
        callButton = (ImageButton) findViewById(R.id.call_button);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PHONE_CALL_PERMISSION_REQUEST:
                if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + contactDetails.getNumbers().get(0)));
                    startActivity(intent);
                }else{

                }
        }
    }
}
