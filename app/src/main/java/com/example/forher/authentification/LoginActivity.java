package com.example.forher.authentification;

import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;

import com.example.forher.MainActivity;
import com.example.forher.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.forher.ui.RaysTestActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button btnLogin;
    FirebaseAuth fAuth;

    //AwesomeValidation awesomeValidation;

    public static final String TAG = "TAG";
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        btnLogin = findViewById(R.id.login_btn);

        TextView createAcc = findViewById(R.id.txt_register);
        fAuth = FirebaseAuth.getInstance();


        //awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(LoginActivity.this, "Please Enter the Email !", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(LoginActivity.this, "Please Enter the Password !", Toast.LENGTH_SHORT).show();
                }
                if (Password.length() < 6) {
                    Toast.makeText(LoginActivity.this, "the Password must be greater than 6 character !", Toast.LENGTH_SHORT).show();
                }
                fAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });


    }


}