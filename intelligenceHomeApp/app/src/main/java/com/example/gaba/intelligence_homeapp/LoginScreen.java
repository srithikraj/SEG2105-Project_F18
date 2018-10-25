package com.example.gaba.intelligence_homeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginScreen extends AppCompatActivity {

    EditText userName;
    EditText pswrd;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        loginButton = findViewById(R.id.loginBtn);
        userName = findViewById(R.id.username);
        pswrd = findViewById(R.id.password);
    }

    public void signUp(View view){
        Intent intent = new Intent(getApplicationContext(),SignupScreen.class);
        startActivityForResult(intent,0);
    }

    //Ended up having to override and make a onClick() method above inside the onCreate that basically does what signIn does
    public void login (View view) {

        // TODO: get from Database
        MyDBHandler dbHandler = new MyDBHandler(this);
        User user = dbHandler.findUser((String) userName.getText().toString());

        if (user!=null && user.getPassword().toString().equals(pswrd.getText().toString())) {
            Intent intent = new Intent(getApplicationContext(),WelcomeScreen.class);

            String nameValue = userName.getText().toString();
            intent.putExtra("Name",nameValue);

            String role = getIntent().getStringExtra("Role");
            intent.putExtra("ROLE",role);

            startActivityForResult(intent,0);

        } else {
            TextView txt = findViewById(R.id.loginErrorTxt);
            txt.setText("Please Type in Valid Login Credentials");
        }
    }

}
