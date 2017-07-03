package com.kareemwaleed.arxicttask.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kareemwaleed.arxicttask.R;
import com.kareemwaleed.arxicttask.database.ArxictDatabaseHandler;

public class CreateAccountActivity extends AppCompatActivity {

    ArxictDatabaseHandler databaseHandler;
    private RelativeLayout mainActivityLayout;
    private TextInputLayout fullNameTextInputLayout;
    private TextInputEditText fullNameTextInputEditText;
    private TextInputLayout emailAddressTextInputLayout;
    private TextInputEditText emailAddressTextInputEditText;
    private TextInputLayout passwordTextInputLayout;
    private TextInputEditText passwordTextInoutEditText;
    private TextInputLayout passwordConfirmationTextInputLayout;
    private TextInputEditText passwordConfirmationTextInputEditText;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        databaseHandler = new ArxictDatabaseHandler(getApplicationContext());
        initViewVars();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainActivityLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fullNameTextInputEditText.getText().toString();
                String email = emailAddressTextInputEditText.getText().toString();
                String password = passwordTextInoutEditText.getText().toString();
                String passwordConfirmation = passwordConfirmationTextInputEditText.getText().toString();
                if (name.equals("") || email.equals("") || password.equals("") || passwordConfirmation.equals("")) {
                    if (name.equals(""))
                        fullNameTextInputLayout.setError("Name field can't be empty");
                    if (email.equals(""))
                        emailAddressTextInputLayout.setError("Name field can't be empty");
                    if (password.equals(""))
                        passwordTextInputLayout.setError("Name field can't be empty");
                    if (passwordConfirmation.equals(""))
                        passwordConfirmationTextInputLayout.setError("Name field can't be empty");
                } else if (!password.equals(passwordConfirmation))
                    passwordConfirmationTextInputLayout.setError("Confirmation doesn't match password");
                else {
                    boolean isSuccessful = databaseHandler.createAccount(name, email, password);
                    if(isSuccessful){
                        Intent intent = new Intent(CreateAccountActivity.this, TabsActivity.class);
                        startActivity(intent);
                        finish();

                    }else
                        Toast.makeText(CreateAccountActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initViewVars(){
        mainActivityLayout = (RelativeLayout) findViewById(R.id.create_account_activity_main_layout);
        fullNameTextInputLayout = (TextInputLayout) findViewById(R.id.full_name_input_layout);
        fullNameTextInputEditText = (TextInputEditText) findViewById(R.id.full_name_edit_text);
        emailAddressTextInputLayout = (TextInputLayout) findViewById(R.id.email_address_input_layout);
        emailAddressTextInputEditText = (TextInputEditText) findViewById(R.id.email_address_edit_text);
        passwordTextInputLayout = (TextInputLayout) findViewById(R.id.password_input_layout);
        passwordTextInoutEditText = (TextInputEditText) findViewById(R.id.password_edit_text);
        passwordConfirmationTextInputLayout = (TextInputLayout) findViewById(R.id.password_confirmation_input_layout);
        passwordConfirmationTextInputEditText = (TextInputEditText) findViewById(R.id.password_confirmation_edit_text);
        createAccountButton = (Button) findViewById(R.id.create_account_button);
    }
}
