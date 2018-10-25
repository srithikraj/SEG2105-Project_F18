package com.example.gaba.intelligence_homeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class WelcomeScreen extends AppCompatActivity {

    TextView userNameDisplay;
    TextView userRoleDisplay;
    TextView adminRoleDisplay;
    String str;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        userRoleDisplay = findViewById(R.id.roleTxt);
        userNameDisplay = findViewById(R.id.userDisplay);
        adminRoleDisplay = findViewById(R.id.adminDisplay);

        MyDBHandler dbHandler = new MyDBHandler(this);
        User user = dbHandler.findUser(getIntent().getStringExtra("Name"));
        str = "Welcome "+user.getUsername()+"!";
        userNameDisplay.setText(str);
        userRoleDisplay.setText(user.getRole() + " Account");
        String ad = "";
        if (user.getRole().equals("Administrator")){
            ad = dbHandler.getAllUsernames();
            adminRoleDisplay.setText(ad);
        }
        else
            adminRoleDisplay.setText("");

    }
}
