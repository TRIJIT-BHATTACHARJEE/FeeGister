package com.example.hp.studentfeesregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StudentList extends AppCompatActivity {

    ListView lv;
    String names[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        lv = (ListView)findViewById(R.id.listView);

        MySQLiteAdapter mySQLiteAdapter=new MySQLiteAdapter(getApplicationContext());
        names=mySQLiteAdapter.DisplayAllRecordsOfStudents();

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.student_names, R.id.textView, names);

        lv.setAdapter(adp);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // position++;
                Intent intent=new Intent(getApplicationContext(),StudentData.class).putExtra("value",names[position]);
                startActivity(intent);


            }

        });

    }
}
