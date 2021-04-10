package com.felix.promtech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.felix.promtech.Db.AppDatabase;
import com.felix.promtech.Db.User;

public class AddNewUserActivity extends AppCompatActivity {

    EditText name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);

        name = findViewById(R.id.input_name);
        email = findViewById(R.id.input_email);
        Button register = findViewById(R.id.signup_btn);

        register.setOnClickListener(v -> OnClickListener());
    }

    private void OnClickListener() {

        addNewUser( name.getText().toString(), email.getText().toString());
    }

    public void addNewUser(String name, String email){
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

        User user = new User();
        user.firstName = name;
        user.email = email;
        db.userDao().insertUser(user);

        finish();
    }
}