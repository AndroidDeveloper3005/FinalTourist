package com.himel.androiddeveloper3005.finaltourist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himel.androiddeveloper3005.finaltourist.Constans;
import com.himel.androiddeveloper3005.finaltourist.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ProgressBar bar;
    private Button registrationButton, loginButton, resetButton;
    private DatabaseReference mDatabaseReference,mDatabaseUser;
    private final String admin_email="amhimel.khan6@gmail.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFireBaseAuth();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (mAuth.getCurrentUser() != null) {
                    startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                    finish();
                }

            }
        };

        setContentView(R.layout.activity_login);
        initView();
        getToolbar();



    }




    @Override
    public void onClick(View v) {
        if (v == registrationButton){
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        }
        else if (v == loginButton){
            bar.setVisibility(View.VISIBLE);
            loginMethod();

        }
        else  if (v == resetButton){
            startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
        }


    }

    private void loginMethod() {

        final String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()){

                            if (email.equals(admin_email)){
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                bar.setVisibility(View.GONE);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                                bar.setVisibility(View.GONE);
                                startActivity(intent);
                                finish();
                            }


                        }




                }
            });
        }
    }


    public void initView() {
        bar = findViewById(R.id.progressBar);
        loginButton = findViewById(R.id.login_button);
        registrationButton = findViewById(R.id.btn_signup);
        resetButton = findViewById(R.id.reset_password);
        inputEmail = findViewById(R.id.email_edittext);
        inputPassword = findViewById(R.id.password_edittext);
        loginButton.setOnClickListener(this);
        registrationButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);




    }

    public void initFireBaseAuth(){
        mAuth = FirebaseAuth.getInstance();
    }
}
