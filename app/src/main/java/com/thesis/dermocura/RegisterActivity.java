package com.thesis.dermocura;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etPassword, etConfirmPassword;
    private ImageView HeaderIcon;
    private Button btnRegister;
    private String name, password, password2;
    private TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // =========================================================================================

        // Variable String
        name = password = password2 = "";

        // Variable Image View
        HeaderIcon = findViewById(R.id.HeaderIcon);

        // Variable Edit Text
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        // Variable Button
        btnRegister = findViewById(R.id.btnRegister);

        // Variable TextView
        tvSignIn = findViewById(R.id.tvSignIn);

        // =========================================================================================

        // Function Switch Light/Dark Mode
        HeaderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemeUtils.toggleTheme(RegisterActivity.this);
            }
        });

        // Function To Continue With The Register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                password2 = etConfirmPassword.getText().toString().trim();
                if (!name.isEmpty() && !password.isEmpty() && !password2.isEmpty()) {
                    if (password.equals(password2)) {
                        Intent intent = new Intent(RegisterActivity.this, EmailActivity.class);
                        intent.putExtra("name", name);
                        intent.putExtra("password", password);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Password Not The Same", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Function To Move To Sign In Page
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}