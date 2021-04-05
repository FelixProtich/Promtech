package com.felix.promtech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button login;
    TextView register, forgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login_btn);
        register = findViewById(R.id.create_account);
        forgotPass = findViewById(R.id.Forgotpass);

        login.setOnClickListener(v -> openHomeActivity());

        register.setOnClickListener(v -> openRegistrationActivity() );

    }

    private void openRegistrationActivity() {
        Intent register = new Intent(this, RegistrationActivity.class);
        startActivity(register);
    }

    public void openHomeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}