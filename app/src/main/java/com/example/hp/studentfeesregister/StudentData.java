package com.example.hp.studentfeesregister;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.StringTokenizer;

public class StudentData extends AppCompatActivity {

    TextView name;
    TextView uid;
    TextView phno;
    TextView email;

    Button delete;
    Button pay;

    long roll;
    String nameofstudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_data);

        name=(TextView)findViewById(R.id.displayname);
        uid=(TextView)findViewById(R.id.displayuid);
        phno=(TextView)findViewById(R.id.displayphn);
        email=(TextView)findViewById(R.id.displayemail);

        delete=(Button)findViewById(R.id.deleterecord);
        pay=(Button)findViewById(R.id.makepayment);


        MySQLiteAdapter mySQLiteAdapter=new MySQLiteAdapter(getApplicationContext());

        String stdname=getIntent().getExtras().getString("value");

        String result_from_db=mySQLiteAdapter.DisplayDataOfStudent(stdname);

       StringTokenizer st=new StringTokenizer(result_from_db,",");

         roll=Long.parseLong(st.nextToken());
        uid.setText("Roll Number : "+roll);

        nameofstudent=st.nextToken();
        name.setText("Name : "+nameofstudent);
        phno.setText("Mobile Number : "+st.nextToken());
        email.setText("Email : "+st.nextToken());




    }

    public void deleteStudent(View view) {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Warning !");
        builder.setMessage("Are you sure you want to delete this student's data ?");
        builder.setPositiveButton("YES",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int id){
                //call sql method to delete
                MySQLiteAdapter obj=new MySQLiteAdapter(getApplicationContext());
                obj.deleteStudent(roll);
                finish();
                Intent intent=new Intent(getApplicationContext(),StudentList.class);
                startActivity(intent);
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){

                    }
                });
        builder.create().show();
    }

    public void makePayment(View view) {

        Intent intent=new Intent(this,PaymentActivity.class);
        intent.putExtra("uid",String.valueOf(roll));
        intent.putExtra("name",nameofstudent);
        startActivity(intent);
        finish();

    }
}
