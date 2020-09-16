package com.capulustech.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity
{
    EditText usernameET, passwordET;
    Button loginBtn;
    CheckBox rememberMeCB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        rememberMeCB = findViewById(R.id.rememberMeCB);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                if (username.equalsIgnoreCase("admin")
                        && password.equalsIgnoreCase("admin"))
                {
                    Toast.makeText(LoginActivity.this,
                            "Login Successful",
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(LoginActivity.this,
                            StudentListActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,
                            "Login Failed",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
