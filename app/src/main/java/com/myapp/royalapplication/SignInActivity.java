package com.myapp.royalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    Button register, signIn;
    TextView forgotPassword;
    EditText email, password;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    Boolean error = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        register = findViewById(R.id.btn_register);
        signIn = findViewById(R.id.btn_sign_in);
        forgotPassword = findViewById(R.id.tv_forgot_password);
        email = findViewById(R.id.edt_signIn_email);
        password = findViewById(R.id.edt_signIn_password);

        firebaseDatabase = FirebaseDatabase.getInstance("https://royal-application-5f683-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference("User");
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this, ForgotActivity.class);
                startActivity(i);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = email.getText().toString();
                String pass = password.getText().toString();

                if (em.length() < 10) {
                    error = true;
                    Toast.makeText(SignInActivity.this, "Enter valid email", Toast.LENGTH_LONG).show();
                }

                if (pass.length() < 5) {
                    error = true;
                    Toast.makeText(SignInActivity.this, "Enter valid password", Toast.LENGTH_LONG).show();
                }

                if (error) {
                    Toast.makeText(SignInActivity.this, "Enter valid credentials", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.signInWithEmailAndPassword(em, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (mAuth.getCurrentUser().isEmailVerified()) {
                                    String uid = mAuth.getUid();
                                    databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @SuppressLint("ApplySharedPref")
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            UserModel userModel = snapshot.getValue(UserModel.class);
                                            String email = userModel.getEmail();
                                            String name = userModel.getName();
                                            String password = userModel.getPassword();
                                            int credits = userModel.getCredits();

                                            SharedPreferences sharedPreferences = getSharedPreferences("MYAPP", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("KEY_NAME", name);
                                            editor.putString("KEY_EMAIL", email);
                                            editor.putString("KEY_PASSWORD", password);
                                            editor.putInt("KEY_CREDITS", credits);
                                            editor.commit();

                                            Intent i = new Intent(SignInActivity.this, NavigationDrawerActivity.class);
                                            startActivity(i);
                                            finish();

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                } else {
                                    Toast.makeText(SignInActivity.this, "Please verify email address or check internet connection", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });


    }
}