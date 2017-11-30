package com.example.disha.wearableproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.disha.wearableproject.helper.DatabaseHistoryHandler;


/**
 * Created by disha on 10/28/2017.
 */


public class RegisterActivity extends Activity {
    private EditText uName;
    private EditText uPhone;
    private EditText uEmail;
    private EditText uPassword;

    String name, phone, email, password;
    boolean userExists;
    private Button btnRegister;
    private ProgressDialog logDialog;
    private DatabaseHistoryHandler db;
    private static final String TAG = "MyActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);

        uName = (EditText) findViewById(R.id.reg_fullname);
        uPhone = (EditText) findViewById(R.id.reg_phone);
        uEmail = (EditText) findViewById(R.id.reg_email);
        uPassword = (EditText) findViewById(R.id.reg_password);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        logDialog = new ProgressDialog(RegisterActivity.this);
        logDialog.setCancelable(false);
        db = new DatabaseHistoryHandler(getApplicationContext());

        btnRegister.setOnClickListener(new Button.OnClickListener() { //Callback method for onclick
            public void onClick(View v) {
                name = uName.getText().toString().trim();
                phone = uPhone.getText().toString().trim();
                email = uEmail.getText().toString().trim();
                password = uPassword.getText().toString().trim();

                if ((!name.equals("")) && (!phone.equals("")) && (!email.equals("")) && (!password.equals("")) ) {
                    new userRegister().execute();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Enter all the details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);

        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    /*Async Task to Send data to server.*/
    private class userRegister extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            logDialog = new ProgressDialog(RegisterActivity.this);
            //nDialog.setTitle("Checking Network");
            logDialog.setMessage("Creating Account..");
            logDialog.setIndeterminate(false);
            logDialog.setCancelable(false);
            logDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            userExists = db.checkUser(email);

            Log.d(TAG, "-User Exists check:" + userExists);

            if (!userExists) {
                db.registerUser(name, phone, email, password);
               // Toast.makeText(getApplicationContext(),
                 //       "Successfully Registered User.", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "-Registered User.");
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (logDialog.isShowing())
                logDialog.dismiss();

            if(userExists ){
                Toast.makeText(getApplicationContext(),
                        "User Already Exists.Registration failed", Toast.LENGTH_SHORT).show();
            }
            else {
                Log.i(TAG, "Launching Login activity.");
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }
    }
}

