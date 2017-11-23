package com.example.disha.wearableproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by disha on 11/10/2017.
 */

public class LoginActivity extends Activity{


    final String[] email = new String[1];
    final String[] password = new String[1];
    EditText uEmail,uPassword;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.login);

        uEmail =(EditText) findViewById(R.id.edtEmail);
        uPassword = (EditText) findViewById(R.id.edtPwd);
        final Button Login = (Button) findViewById(R.id.btnLogin);
        Login.setOnClickListener(new Button.OnClickListener() { //Callback method for onclick
            public void onClick(View v) {
              email[0] = uEmail.getText().toString().trim();
                password[0] = uPassword.getText().toString().trim();

                if ((!email[0].equals("")) && (!password[0].equals(""))) {
                            Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
                            startActivity(i);
                            finish();
                } else if ((!email[0].equals(""))) {
                    Toast.makeText(getApplicationContext(),
                            "Enter Password", Toast.LENGTH_SHORT).show();
                } else if ((!password[0].equals(""))) {
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
}
