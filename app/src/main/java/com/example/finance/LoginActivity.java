package com.example.finance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = this.findViewById(R.id.usernameEditText);
        passwordEditText = this.findViewById(R.id.passwordEditText);
        loginButton = this.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // TODO: Implement login logic here once we get real login
                // For now, we'll just allow everything to get into the application
                //

                // Start the MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}