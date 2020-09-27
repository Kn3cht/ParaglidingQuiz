package com.example.paraglidingquiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.paraglidingquiz.Model.User;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText username;
    EditText password;

    // Login
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    public void login(View view) {
        User user = new User(username.getText().toString(), password.getText().toString());
        // TODO uncomment
        //if (!user.getError().equals(""))
        //    Toast.makeText(this, "Passwort oder Benutzername nicht eingegeben", Toast.LENGTH_SHORT).show();
        //else
        UserLoginFunction(user.getUsername(), user.getPassword());
    }

    public void UserLoginFunction(final String email, final String password){

        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(LoginActivity.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

//                if (httpResponseMsg.equalsIgnoreCase("Data Matched")) {
                    finish();
                    Intent intent = new Intent(LoginActivity.this, UpdateService.class);
//                  intent.putExtra(UserEmail, email);
                    startActivity(intent);
                    finish();
 //               } else {
 //                   Toast.makeText(LoginActivity.this, httpResponseMsg, Toast.LENGTH_LONG).show();
 //               }

            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("email", params[0]);
                hashMap.put("password", params[1]);

                //finalResult = httpParse.postRequest(hashMap, HttpURL);
                finalResult = "Data Matched";
                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(email,password);
    }
}
