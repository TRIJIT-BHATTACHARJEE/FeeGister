package com.example.hp.studentfeesregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentDetails extends AppCompatActivity {

    EditText stdname;
    EditText stdph;
    EditText stdemail;
    Button save;

    String nameofstudent="";
    String phonenumber="";
    String emailid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        stdname = (EditText)findViewById(R.id.stdname);
        stdph = (EditText)findViewById(R.id.phno);
        stdemail = (EditText)findViewById(R.id.emailid);
        save = (Button)findViewById(R.id.addstudent);

    }

    public void saveStudentRecord(View view) {



       nameofstudent=stdname.getText().toString().trim();
        phonenumber=stdph.getText().toString().trim();
        emailid=stdemail.getText().toString().trim();

        if(validityChecker()==false)//that means no invalid items found
        {


            MySQLiteAdapter obj = new MySQLiteAdapter(getApplicationContext());
            long id = obj.InsertRecordStudent(nameofstudent, phonenumber, emailid);

            if (id < 0)
                MyToastMessage.myMessage(getApplicationContext(), "Sorry, some error occured");
            else
                MyToastMessage.myMessage(getApplicationContext(), "Save Successful");

            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            finish();
        }



    }

    private boolean validityChecker(){
        boolean status=false; //false means no probs

        //validity check

        if(nameofstudent.length()==0 || phonenumber.length()==0 || emailid.length()==0){

            MyToastMessage.myMessage(this,"Fields cant be empty");
            status=true;
            return status;
        }

        String invalidchars="0123456789.,;<>:\"[]{}=+-_)(*&^%$#@!~`";
        for(int i=0;i<nameofstudent.length();i++)
        {
            if(invalidchars.indexOf(nameofstudent.charAt(i))!=-1)
            {
                stdname.setText("");
                stdname.setError("Invalid Name");
                status=true;
                return status;
            }

        }

        if(phonenumber.length()!=10){
            stdph.setText("");
            stdph.setError("Invalid Mobile Number");
            status=true;
            return status;
        }

        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN="^[_A-Za-z0-9]+(\\.[_A-Za-z0-9]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern=Pattern.compile(EMAIL_PATTERN);
        matcher=pattern.matcher(emailid);
        if(!matcher.matches()){
            stdemail.setText("");
            stdemail.setError("Invalid email id");
            status=true;
            return status;
        }
        return  status;
    }
}
