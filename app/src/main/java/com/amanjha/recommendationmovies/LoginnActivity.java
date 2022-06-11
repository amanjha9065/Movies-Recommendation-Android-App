package com.amanjha.recommendationmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginnActivity extends AppCompatActivity {

    EditText userName, password;
    Button btnLogin, btnRegister;
    DBHelper DB;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginn);
        Intent intent = getIntent();

        sharedPreferences = getSharedPreferences("currentUser", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();

        userName = findViewById(R.id.username_login);
        password = findViewById(R.id.password_login);
        btnLogin = findViewById(R.id.btnLogin_login);
        btnRegister = findViewById(R.id.btnRegister_login);
        DB = new DBHelper(this);

        if (intent != null) {
            userName.setText(intent.getStringExtra("user_name"));
            password.setText(intent.getStringExtra("user_pass"));
        }
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginnActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userName.getText().toString();
                String pass = password.getText().toString();


                if (user.equals("") || pass.equals(""))
                    Toast.makeText(LoginnActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass = DB.checkUsernamePassword(user, pass);
                    if (checkuserpass == true) {
                        Toast.makeText(LoginnActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        myEdit.putBoolean("isLoggedIn", true);
                        myEdit.apply();
                        Intent intent = new Intent(LoginnActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(LoginnActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

    }

}