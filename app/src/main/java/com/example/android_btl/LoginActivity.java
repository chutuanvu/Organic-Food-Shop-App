package com.example.android_btl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_btl.DAO.UserDAO;
import com.example.android_btl.Model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText txtUsername,txtPassword;
    private CheckBox rememberMeCheckBox;
    UserDAO userDAO;
    private MaterialButton btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userDAO = new UserDAO(this);
        txtUsername = findViewById(R.id.txt_username);
        txtPassword = findViewById(R.id.txt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(view -> {
            String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();

            if (username != null && password != null) {
                User user = userDAO.login(username, password);
                if (user != null) {
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userid", user.getUser_id());
                    editor.putString("username", user.getUser_name());
                    editor.putString("email", user.getEmail());
                    byte[] imageBytes = user.getImg();
                    if (imageBytes != null) {
                        String base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        editor.putString("profileimage", base64Image);
                    } else {
                        editor.remove("profileimage");
                    }
                    editor.apply();
                    String role = user.getRole();
                    Intent intent;
                    if ("admin".equals(role)) {
                        intent = new Intent(LoginActivity.this, AdminMainActivity.class);
                    } else {
                        intent = new Intent(LoginActivity.this, UserMainActivity.class);
                    }
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}