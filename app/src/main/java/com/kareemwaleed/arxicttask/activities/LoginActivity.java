package com.kareemwaleed.arxicttask.activities;

import android.content.Context;
import android.content.Intent;
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

import com.kareemwaleed.arxicttask.CreateAccountActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHandler = new ArxictDatabaseHandler(getApplicationContext());
        initViewVars();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
        activityMainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailAddressTextInputEditText.getText().toString();
                String password = passwordTextInputEditText.getText().toString();
                if(email.equals("") && password.equals("")){
                    emailAddressTextInputLayout.setError("Email address field can't be empty");
                    passwordTextInputLayout.setError("Password field can't be empty");
                }else if(email.equals(""))
                    emailAddressTextInputLayout.setError("Email address field can't be empty");
                else if(password.equals(""))
                    passwordTextInputLayout.setError("Password field can't be empty");
                else{
                    boolean isSuccessful = databaseHandler.login(email, password);
                    if(isSuccessful){
                        Intent intent = new Intent(LoginActivity.this, TabsActivity.class);
                        startActivity(intent);
                        finish();
                    }else
                        passwordTextInputLayout.setError("The password entered doesn't the email");
                }
            }
        });

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

    private void initViewVars(){
        activityMainLayout = (RelativeLayout) findViewById(R.id.login_activity_main_layout);
        emailAddressTextInputLayout = (TextInputLayout) findViewById(R.id.email_address_input_layout);
        emailAddressTextInputEditText = (TextInputEditText) findViewById(R.id.email_address_edit_text);
        passwordTextInputLayout = (TextInputLayout) findViewById(R.id.password_input_layout);
        passwordTextInputEditText = (TextInputEditText) findViewById(R.id.password_edit_text);
        loginButton = (Button) findViewById(R.id.login_button);
        createAccountButton = (Button) findViewById(R.id.create_account_button);
    }
}
