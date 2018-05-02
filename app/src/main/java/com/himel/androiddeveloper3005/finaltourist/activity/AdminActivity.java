package com.himel.androiddeveloper3005.finaltourist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.himel.androiddeveloper3005.finaltourist.R;

public class AdminActivity extends BaseActivity implements View.OnClickListener{
    private DatabaseReference mDatabaseRef;
    private ProgressBar bar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthState;
    private DatabaseReference mDatabaseUsers;
    private Button setup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getToolbar();
        initView();
        initFireBaseAuth();

        mAuthState = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (mAuth.getCurrentUser() == null) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }

            }
        };






    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthState);
    }

    public void initView() {
        bar = findViewById(R.id.progressBar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        setup = findViewById(R.id.goto_setup);
        setup.setOnClickListener(this);



    }

    public void initFireBaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        /*mDatabaseRef = FirebaseDatabase.getInstance().getReference().child(Constans.POST_DATABSE_PATH);
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child(Constans.USER_DATABSE_PATH);
        mDatabaseRef.keepSynced(true);
        mDatabaseUsers.keepSynced(true);*/





    }

    @Override
    public void onClick(View v) {

       if (v == setup){
            finish();
            startActivity(new Intent(getApplicationContext(),SetupActivity.class));

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_manu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logOutItem){
            logout();
        }
        else if (item.getItemId() == R.id.Account_Setup){
            Intent accountIntent = new Intent(getApplicationContext(),SetupActivity.class);
            accountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(accountIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        mAuth.signOut();

    }
}


