package com.example.hp.studentfeesregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class PaymentActivity extends AppCompatActivity {

    TextView dispname,dispuid;
    Button save,cancel;
    Spinner mm,yyyy;

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        dispname=(TextView)findViewById(R.id.displayname);
        dispuid=(TextView)findViewById(R.id.displayid);

        editText=(EditText)findViewById(R.id.editText);

        save=(Button)findViewById(R.id.save_payment);
        cancel=(Button)findViewById(R.id.cancel_payment);

        mm=(Spinner)findViewById(R.id.spinner_month);
        yyyy=(Spinner)findViewById(R.id.spinner_year);

        String months[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        ArrayAdapter<String> adpater1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,months);
        mm.setAdapter(adpater1);

        String years[]=new String[5];
        int index=0;
        for(int i=2016;i<=2020;i++)
            years[index++]=String.valueOf(i);

        ArrayAdapter<String> adpater2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,years);
        yyyy.setAdapter(adpater2);

        dispname.setText("Name : "+getIntent().getExtras().getString("name"));
        dispuid.setText("ID : "+getIntent().getExtras().getString("uid"));

    }

    public void savePayment(View view) {
        MySQLiteAdapter mySQLiteAdapter=new MySQLiteAdapter(getApplicationContext());
         int id= Integer.parseInt(getIntent().getExtras().getString("uid"));
         String stu_name= getIntent().getExtras().getString("name");
         String month= mm.getItemAtPosition(mm.getSelectedItemPosition()).toString();
        String year= yyyy.getItemAtPosition(yyyy.getSelectedItemPosition()).toString();
        int fees=Integer.parseInt(editText.getText().toString());

        String today_date=new SimpleDateFormat("dd-MMM-yyyy").format(new Date()).toString();

        long fromdb=mySQLiteAdapter.InsertRecordPayment(id,month,year,today_date,Integer.parseInt(editText.getText().toString()));

        if(fromdb>0) {
            MyToastMessage.myMessage(this, "Successful Transaction");

            //SMS SENDING CODE

            StringTokenizer st=new StringTokenizer(stu_name);
            stu_name=st.nextToken();
            String message="Thanks "+stu_name+" for your payment of Rs. "+fees+" for "+month+", "+year+", received on "+today_date+".";
            SmsManager smsManager=SmsManager.getSmsManagerForSubscriptionId(2);
            String phone=mySQLiteAdapter.getPhoneNumber(id);
            smsManager.sendTextMessage(phone,null,message,null,null);
            MyToastMessage.myMessage(this,"Acknowledge Message Sent");
        }
            else
            MyToastMessage.myMessage(this,"Sorry! Some error occured");

        Intent intent=new Intent(this,StudentList.class);
        startActivity(intent);
        finish();

    }

    public void cancelPayment(View view) {
        Intent intent=new Intent(this,StudentList.class);
        startActivity(intent);
        finish();

    }
}
