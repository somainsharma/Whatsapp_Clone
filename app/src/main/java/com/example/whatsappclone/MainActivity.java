package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtemail, edtphone, edtpassword;
    private Button btnsignup, btntowardsloginpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtemail = findViewById(R.id.edtemailsignup);
        edtphone = findViewById(R.id.edtphonesignup);
        edtpassword = findViewById(R.id.edtpasswordsignup);

        btnsignup = findViewById(R.id.btnsignup);
        btntowardsloginpage = findViewById(R.id.btnloginonsignup);

        btnsignup.setOnClickListener(MainActivity.this);
        btntowardsloginpage.setOnClickListener(MainActivity.this);

        if(ParseUser.getCurrentUser() != null){
            Intent intent = new Intent(this,PrimaryActivity.class);
            startActivity(intent);
            finish();
        }


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.btnsignup:

                final ParseUser appuser = new ParseUser();
                appuser.setEmail(edtemail.getText().toString());
                appuser.setUsername(edtphone.getText().toString());
                appuser.setPassword(edtpassword.getText().toString());

                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Signing in the user: " + edtemail.getText());
                progressDialog.show();

                appuser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){

                            Toast.makeText(MainActivity.this," You have sucessfully signed up",
                                    Toast.LENGTH_SHORT).show();
                            TransitionOfActivity();

                        }else{
                            Toast.makeText(MainActivity.this," An error occurred" + "\n" + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.cancel();
                    }
                });
                break;

            case R.id.btnloginonsignup:
                Intent intent = new Intent(MainActivity.this,login_page.class);
                startActivity(intent);
                break;
        }
    }

    private void TransitionOfActivity(){
        Intent intent = new Intent(MainActivity.this,PrimaryActivity.class);
        startActivity(intent);
    }

}
