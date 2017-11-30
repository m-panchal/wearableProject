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
 * Created by disha on 11/10/2017.
 */

public class LoginActivity extends Activity{

    private EditText uEmail;
    private EditText uPassword;
    private Button btnLogin;

    String email, password;

    boolean userValid;
    private ProgressDialog logDialog;
    private DatabaseHistoryHandler db;
    private static final String TAG = "MyActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.login);

        uEmail =(EditText) findViewById(R.id.edtEmail);
        uPassword = (EditText) findViewById(R.id.edtPwd);

        db = new DatabaseHistoryHandler(getApplicationContext());
        logDialog = new ProgressDialog(LoginActivity.this);
        logDialog.setCancelable(false);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new Button.OnClickListener() { //Callback method for onclick
            public void onClick(View v) {
                email = uEmail.getText().toString().trim();
                password = uPassword.getText().toString().trim();

                Log.d(TAG,"-email:" +email);
                Log.d(TAG,"-password:"+password);

                if ((!email.equals("")) && (!password.equals(""))) {
                    new userLogin().execute();
                } else if ((!email.equals(""))) {
                    Toast.makeText(getApplicationContext(),
                            "Enter Password", Toast.LENGTH_SHORT).show();
                } else if ((!password.equals(""))) {
                    Toast.makeText(getApplicationContext(),
                            "Enter Email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Enter the Email and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView loginScreen = (TextView) findViewById(R.id.link_to_register);
        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    /*Async Task to Send data to server.*/
    private class userLogin extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            logDialog = new ProgressDialog(LoginActivity.this);
            //nDialog.setTitle("Checking Network");
            logDialog.setMessage("Logging in...");
            logDialog.setIndeterminate(false);
            logDialog.setCancelable(false);
            logDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            userValid = db.checkUserLogin(email,password);
            Log.d(TAG, "-User Login check:" + userValid);
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (logDialog.isShowing())
                logDialog.dismiss();

            if(!userValid ){
                Toast.makeText(getApplicationContext(),
                        "Login Failed!", Toast.LENGTH_SHORT).show();
            }
            else {
                Log.i(TAG, "Launching History activity.");
                Intent i = new Intent(LoginActivity.this, HistoryActivity.class);
                startActivity(i);
                finish();
            }
        }
    }
}
