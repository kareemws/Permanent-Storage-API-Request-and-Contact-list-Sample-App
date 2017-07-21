package com.kareemwaleed.arxicttask.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.kareemwaleed.arxicttask.R;
import com.kareemwaleed.arxicttask.database.ArxictDatabaseHandler;

public class LoginActivity extends AppCompatActivity {

    private ArxictDatabaseHandler databaseHandler;
    private RelativeLayout activityMainLayout;
    private TextInputLayout emailAddressTextInputLayout;
    private TextInputEditText emailAddressTextInputEditText;
    private TextInputLayout passwordTextInputLayout;
    private TextInputEditText passwordTextInputEditText;
    private Button loginButton;
    private Button createAccountButton;
    private SharedPreferences user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = getSharedPreferences("user", MODE_PRIVATE);
        /**
         * Checking the shared preference to see whether there is a user logged in or not, if
         * a user has logged in previously the Lists show up directly else the login page shows up
         */
        if(user.getBoolean("login_status", false)){
            Intent intent = new Intent(LoginActivity.this, TabsActivity.class);
            startActivity(intent);
            finish();
        }
        databaseHandler = new ArxictDatabaseHandler(getApplicationContext());
        initViewVars();
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         * Handles the create account text in the bottom of the login page click
         */
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        /**
         * Handles the touch outside any text view to dismiss the soft keyboard
         */
        activityMainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });


        /**
         * Handles the login button click.
         * Validates the information given, if right start the lists else show error message
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailAddressTextInputEditText.getText().toString();
                String password = passwordTextInputEditText.getText().toString();
                if(email.equals("") && password.equals("")){
                    passwordTextInputLayout.setError("Password field can't be empty");
                }else if(email.equals(""))
                    emailAddressTextInputLayout.setError("Email address field can't be empty");
                else if(password.equals(""))
                    passwordTextInputLayout.setError("Password field can't be empty");
                else{
                    boolean isSuccessful = databaseHandler.login(email, password);
                    if(isSuccessful){
                        user.edit().putBoolean("login_status", true).apply();
                        Intent intent = new Intent(LoginActivity.this, TabsActivity.class);
                        startActivity(intent);
                        finish();
                    }else
                        passwordTextInputLayout.setError("The password entered doesn't the email");
                }
            }
        });

        /**
         * Handles the case of dismissing the error message shown when the user tries to modify what was the cause
         * of it
         */
        emailAddressTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailAddressTextInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordTextInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    /**
     * Initializes view variables
     */
    private void initViewVars(){
        activityMainLayout = (RelativeLayout) findViewById(R.id.login_activity_main_layout);
        emailAddressTextInputLayout = (TextInputLayout) findViewById(R.id.email_address_input_layout);
        emailAddressTextInputEditText = (TextInputEditText) findViewById(R.id.email_address_edit_text);
        passwordTextInputLayout = (TextInputLayout) findViewById(R.id.password_input_layout);
        passwordTextInputEditText = (TextInputEditText) findViewById(R.id.password_edit_text);
        loginButton = (Button) findViewById(R.id.login_button);
        createAccountButton = (Button) findViewById(R.id.create_account_button);
    }


    /**
     * Receives the sign up intent result to handle the case of a user sign up and gets directed to the list
     * without this handling the back button press will take the user to the login screen again but with the handling
     * the back button press will terminate the app
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK)
            finish();
    }
}
