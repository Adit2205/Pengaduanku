package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class Register extends AppCompatActivity {


    private EditText etUsername, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(Register.this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(Register.this, "Password tidak cocok", Toast.LENGTH_SHORT).show();
                } else {
                    // Validasi username
                    // Kode di bawah ini tidak menggunakan kode `isUsernameAvailable()`
                    // Kode ini akan mencoba membuat user baru, jika username sudah digunakan,
                    // maka akan muncul error
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Register.this, task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    // Set username
                                    user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(username).build());

                                    // Pindah ke activity utama
                                    Intent intent = new Intent(Register.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Log.d("TAG", "createUserWithEmail:failure", task.getException());
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        // Username sudah digunakan
                                        // Tampilkan pesan kepada pengguna
                                        String message = "Username sudah digunakan. Silahkan pilih username lain yang berbeda dari username yang sudah ada.";
                                        Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Error lainnya
                                        Toast.makeText(Register.this, "Gagal mendaftar", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });







    }

}



