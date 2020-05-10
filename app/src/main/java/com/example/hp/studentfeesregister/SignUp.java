package com.example.hp.studentfeesregister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {

    EditText username,password,passwordcnf,phone;
    Button signupbtn;

    SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username=(EditText)findViewById(R.id.edittext_username);
        password=(EditText)findViewById(R.id.edittext_password);
        passwordcnf=(EditText)findViewById(R.id.edittext_password_cnf);
        phone=(EditText)findViewById(R.id.edittext_phone);

        signupbtn=(Button)findViewById(R.id.button_signup);

        sharedPrefs = getSharedPreferences("sharedprefs", 0);
        boolean completed = sharedPrefs.getBoolean("already_signed", false);
        if (completed == true) {
            Intent intent = new Intent(this,Home.class);

            startActivity(intent);
            finish();
        }

    }

    public void SignUpClick(View view) {

        if(!validityCheck()){
            SharedPreferences.Editor editors = sharedPrefs.edit();
            editors.putString("user_name",username.getText().toString());
            editors.putString("password",password.getText().toString());
            editors.putString("my_phone",phone.getText().toString());
            editors.putBoolean("already_signed",true);
            editors.apply();

            Intent intent=new Intent(this,Home.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean validityCheck() {
        boolean status=false;//false indicates no probs
        if(username.getText().length()==0 || password.getText().length()==0 || passwordcnf.getText().length()==0 || phone.getText().length()==0){
            MyToastMessage.myMessage(this,"Fields cant be empty");
            status=true;
            return status;
        }
        if((password.getText().toString()).equals(passwordcnf.getText().toString())==false){
            password.setText("");
            passwordcnf.setText("");
            password.setError("Password is not matching");
            passwordcnf.setError("Password is not matching");
            status=true;
            return  status;
        }
        if(phone.getText().length()!=10){
            phone.setText("");
            phone.setError("Invalid Mobile Number");
            status=true;
            return status;
        }
        return  status;
    }
}
