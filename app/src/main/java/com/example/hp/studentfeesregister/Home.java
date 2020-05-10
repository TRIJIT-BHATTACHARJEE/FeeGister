package com.example.hp.studentfeesregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void addNewStudent(View view) {

        Intent intent = new Intent(getApplicationContext(),StudentDetails.class);
        startActivity(intent);

    }




    public void viewAllStudents(View view) {
        Intent intent = new Intent(getApplicationContext(),StudentList.class);
        startActivity(intent);
    }
}
