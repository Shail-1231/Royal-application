package com.myapp.royalapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    Button signUp;
    EditText name, email, password, country, state, city, schoolName, phoneNumber;
    Boolean error = false;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp = findViewById(R.id.btn_sign_up);
        name = findViewById(R.id.edt_first_name);
        email = findViewById(R.id.edt_email);
        password = findViewById(R.id.edt_password);
        country = findViewById(R.id.edt_country);
        state = findViewById(R.id.edt_state);
        city = findViewById(R.id.edt_city);
        schoolName = findViewById(R.id.edt_school_name);
        phoneNumber = findViewById(R.id.edt_phone_number);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://royal-application-5f683-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference("User");


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nm = name.getText().toString();
                String em = email.getText().toString();
                String pass = password.getText().toString();
                String cnt = country.getText().toString();
                String cty = city.getText().toString();
                String ste = state.getText().toString();
                String pNum = phoneNumber.getText().toString();
                String sName = schoolName.getText().toString();

                if (nm.length() < 2) {
                    Toast.makeText(SignUpActivity.this, "Enter name again", Toast.LENGTH_LONG).show();
                    error = true;
                } else {
                    error = false;
                }

                if (em.length() < 10) {
                    Toast.makeText(SignUpActivity.this, "Enter email again", Toast.LENGTH_LONG).show();
                    error = true;
                } else {
                    error = false;
                }

                if (pass.length() < 5) {
                    Toast.makeText(SignUpActivity.this, "Enter password of more then 5 characters ", Toast.LENGTH_LONG).show();
                    error = true;
                } else {
                    error = false;
                }

                if (cnt.length() < 2) {
                    Toast.makeText(SignUpActivity.this, "Enter country again", Toast.LENGTH_LONG).show();
                    error = true;
                } else {
                    error = false;
                }

                if (ste.length() < 2) {
                    Toast.makeText(SignUpActivity.this, "Enter state again", Toast.LENGTH_LONG).show();
                    error = true;
                } else {
                    error = false;
                }

                if (cty.length() < 2) {
                    Toast.makeText(SignUpActivity.this, "Enter city again", Toast.LENGTH_LONG).show();
                    error = true;
                } else {
                    error = false;
                }

                if (pNum.length() < 7) {
                    Toast.makeText(SignUpActivity.this, "Enter phone number again", Toast.LENGTH_LONG).show();
                    error = true;
                } else {
                    error = false;
                }

                if (sName.length() < 2) {
                    Toast.makeText(SignUpActivity.this, "Enter school name again", Toast.LENGTH_LONG).show();
                    error = true;
                } else {
                    error = false;
                }

                if (error) {
                    Toast.makeText(SignUpActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();

                } else {
                    mAuth.createUserWithEmailAndPassword(em,pass).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "The user already exists", Toast.LENGTH_LONG).show();
                            } else {
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            String id = mAuth.getUid();
                                            UserModel userModel = new UserModel();
                                            userModel.setName(nm);
                                            userModel.setEmail(em);
                                            userModel.setPassword(pass);
                                            userModel.setPhoneNumber(pNum);
                                            userModel.setCountry(cnt);
                                            userModel.setState(ste);
                                            userModel.setCity(cty);
                                            userModel.setSchoolName(sName);
                                            userModel.setCredits(0);
                                            userModel.setId(id);
                                            databaseReference.child(id).setValue(userModel);
                                            Toast.makeText(SignUpActivity.this, "Please check your email for verification!!!", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                    });

                }
            }
        });
    }
}