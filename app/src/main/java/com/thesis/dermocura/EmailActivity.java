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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EmailActivity extends AppCompatActivity {

    private ImageView HeaderIcon;
    private TextView tvSkipEmail;
    private EditText etEmail;
    private Button btnRegister;
    private String name, password, email;
    private final String URL = "http://192.168.1.7/thesis/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_email);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // =========================================================================================

        // Receive Variables From RegisterActivity
        Intent receivedIntent = getIntent();
        String rcvName = receivedIntent.getStringExtra("name");
        String rcvPassword = receivedIntent.getStringExtra("password");

        // =========================================================================================

        // Variable String
        name = password = email = "";

        // Variable Image View
        HeaderIcon = findViewById(R.id.HeaderIcon);

        // Variable Edit Text
        etEmail = findViewById(R.id.etEmail);

        // Variable Buttons
        btnRegister = findViewById(R.id.btnRegister);

        // Variable TextView
        tvSkipEmail = findViewById(R.id.tvSkipEmail);

        // =========================================================================================

        // Function Switch Light/Dark Mode
        HeaderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemeUtils.toggleTheme(EmailActivity.this);
            }
        });

        // Functions For Register Button With Email
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString().trim();
                name = rcvName;
                password = rcvPassword;
                if (!email.isEmpty()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")) {
                                Toast.makeText(EmailActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                            } else if (response.equals("failure")) {
                                Toast.makeText(EmailActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(EmailActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> data = new HashMap<>();
                            data.put("name", name);
                            data.put("password", password);
                            data.put("email", email);
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(EmailActivity.this, "Email is Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Function To Skip Adding Email
        tvSkipEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = rcvName;
                password = rcvPassword;
                email = "null";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(EmailActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                        } else if (response.equals("failure")) {
                            Toast.makeText(EmailActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EmailActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("name", name);
                        data.put("password", password);
                        data.put("email", email);
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
    }
}