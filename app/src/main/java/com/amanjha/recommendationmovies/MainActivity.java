package com.amanjha.recommendationmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText userName, password, rePassword;
    Button btnRegister, btnLogin;
    DBHelper DB;
    SharedPreferences sh;
    SharedPreferences.Editor myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sh = getSharedPreferences("currentUser", MODE_PRIVATE);
        myEdit = sh.edit();

        Boolean isLoggedIn = sh.getBoolean("isLoggedIn", false);

        if(isLoggedIn == true) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }


        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.repassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        DB = new DBHelper(this);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userName.getText().toString();
                String pass = password.getText().toString();
                String repass = rePassword.getText().toString();

                if( user.equals("") || pass.equals("") || repass.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    if(pass.equals(repass)) {
                        Boolean checkUsername = DB.checkUsername(user);
                        if(checkUsername == false) {
                            Boolean insert = DB.insertData(user, pass);
                            if(insert == true) {
                                Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginnActivity.class);
                                intent.putExtra("user_name", user);
                                intent.putExtra("user_pass", pass);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(MainActivity.this, "User already exist, Please Login...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginnActivity.class);
                            intent.putExtra("user_name", user);
                            intent.putExtra("user_pass", pass);
                            startActivity(intent);
                            finish();

                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Passwords not matches", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginnActivity.class);
                startActivity(intent);
            }
        });
    }


}