package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class login_page extends AppCompatActivity implements View.OnClickListener {

    private EditText edtphoneusername, edtpassword;
    private Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        edtphoneusername = findViewById(R.id.edtphonelogin);
        edtpassword = findViewById(R.id.edtpasswordloginpage);

        btnlogin = findViewById(R.id.btnloginonlogin);
        btnlogin.setOnClickListener(login_page.this);

        if(ParseUser.getCurrentUser() != null){

            //  ParseUser.getCurrentUser().logOut();
            Intent intent = new Intent(login_page.this,PrimaryActivity.class);
            startActivity(intent);


        }

        //THis can be buggy
        // Copied from mainactiity of insta
        edtpassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){

                    onClick(btnlogin);

                }
                return false;
            }
        });


    }

    @Override
    public void onClick(View view) {
        ParseUser.logInInBackground(edtphoneusername.getText().toString(),
                edtpassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user != null && e == null){
                            Toast.makeText(login_page.this, user.getUsername()
                                    + " has successfully logged in ", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(login_page.this,PrimaryActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else{

                            Toast.makeText(login_page.this, "An error occurred" +"\n"
                                    + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });




    }
}
