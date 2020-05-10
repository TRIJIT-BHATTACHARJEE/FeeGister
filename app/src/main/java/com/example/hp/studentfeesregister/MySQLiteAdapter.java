package com.example.hp.studentfeesregister;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.StringTokenizer;

public class MySQLiteAdapter {
    Context context;
    MySQLiteOpenHelper mySQLiteOpenHelper;

    public MySQLiteAdapter(Context context) {
        this.context=context;
        mySQLiteOpenHelper=new MySQLiteOpenHelper(context);
    }

    //add a new student record in database
    public long InsertRecordStudent(String studentname,String phno,String email) {
        SQLiteDatabase db=mySQLiteOpenHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(mySQLiteOpenHelper.NAME,studentname);
        contentValues.put(mySQLiteOpenHelper.PHONE,phno);
        contentValues.put(mySQLiteOpenHelper.EMAIL,email);

        long id=db.insert(MySQLiteOpenHelper.TABLE_NAME_STUDENT,null,contentValues);
        return id;
    }

    //add a new payment transaction details in database
   public long InsertRecordPayment(int stdid, String month, String year, String dateofpayment,int fees) {
        SQLiteDatabase db=mySQLiteOpenHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(mySQLiteOpenHelper.UID1,stdid);
        contentValues.put(mySQLiteOpenHelper.MONTH,month);
       contentValues.put(mySQLiteOpenHelper.YEAR,year);
       contentValues.put(mySQLiteOpenHelper.DATEOFPAYMENT,dateofpayment);
       contentValues.put(mySQLiteOpenHelper.FEES,fees);


        long id=db.insert(MySQLiteOpenHelper.TABLE_NAME_PAYMENT,null,contentValues);
        return id;
    }

    //get the phone number of student by searching his id number
    public String getPhoneNumber(int id){
        SQLiteDatabase db=mySQLiteOpenHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select phno from student where student_id = "+id,null);
        String phno="";
        while(cursor.moveToNext()){
            phno=cursor.getString(0);
            break;
        }
        return phno;
    }


    //display names and thier id of students
    public String[] DisplayAllRecordsOfStudents() {
        int position=0;
        String arr[];
        SQLiteDatabase db=mySQLiteOpenHelper.getWritableDatabase();
        long rows= DatabaseUtils.queryNumEntries(db,MySQLiteOpenHelper.TABLE_NAME_STUDENT);

        arr=new String[(int)rows];
        String[] columns={MySQLiteOpenHelper.UID,MySQLiteOpenHelper.NAME,MySQLiteOpenHelper.PHONE,MySQLiteOpenHelper.EMAIL};
        Cursor cursor=db.query(MySQLiteOpenHelper.TABLE_NAME_STUDENT,columns,null,null,null,null,null);
        StringBuffer buffer=new StringBuffer();
        while(cursor.moveToNext()) {

            int uid=cursor.getInt(0);
            String name=cursor.getString(1);
            String phone=cursor.getString(2);
            String email=cursor.getString(3);
            arr[position++]=name+" ["+String.valueOf(uid)+"]";

           // buffer.append(uid+" | "+name+" | "+phone+" | "+email+" \n");
        }

        return arr;
       // return buffer.toString();
    }

    //display all data of a particular student
    public String DisplayDataOfStudent(String stdname) {

        StringTokenizer stringTokenizer=new StringTokenizer(stdname,"[]");
        String nameonly=stringTokenizer.nextToken();
        String roll=stringTokenizer.nextToken();

        SQLiteDatabase db=mySQLiteOpenHelper.getWritableDatabase();

        String[] columns={MySQLiteOpenHelper.UID,MySQLiteOpenHelper.NAME,MySQLiteOpenHelper.PHONE,MySQLiteOpenHelper.EMAIL};
       // Cursor cursor=db.query(MySQLiteOpenHelper.TABLE_NAME_STUDENT,columns,null,null,null,null,null);
        Cursor cursor=db.rawQuery("select student_id, name, phno, email from student where student_id = "+roll,null);
        StringBuffer buffer=new StringBuffer();

                if(cursor!=null) {
                    cursor.moveToFirst();
                    int uid = cursor.getInt(0);
                    String name = cursor.getString(1);
                    String phone = cursor.getString(2);
                    String email = cursor.getString(3);


                    buffer.append(uid + "," + name + "," + phone + "," + email + ",");
                }


                return buffer.toString();


    }

    //delete a student
    public void deleteStudent(long id){
        String uid=String.valueOf(id);
        SQLiteDatabase db=mySQLiteOpenHelper.getWritableDatabase();
        db.delete(mySQLiteOpenHelper.TABLE_NAME_STUDENT, mySQLiteOpenHelper.UID + "=" + id, null);
        MyToastMessage.myMessage(context,"Deleted");
    }









    public class MySQLiteOpenHelper extends SQLiteOpenHelper {

        //STUDENT DATABASE
        private static final String DATABASE_NAME = "StudentFeesRecords.db";
        private static final String TABLE_NAME_STUDENT = "student";
        private static final int DATABASE_VERSION1 = 3;
        private static final String UID = "student_id";
        private static final String NAME= "name";
        private static final String PHONE = "phno";
        private static final String EMAIL= "email";

        private static final String CREATE_TABLE_STUDENT = "CREATE TABLE " + TABLE_NAME_STUDENT + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME+ " VARCHAR(255), " + PHONE+ " VARCHAR(255), "+EMAIL+" VARCHAR(255));";
        private static final String DROP_TABLE_STUDENT = "DROP TABLE IF EXISTS " + TABLE_NAME_STUDENT + ";";


        //PAYMENT DATABASE
        private static final String TABLE_NAME_PAYMENT = "payment";
        private static final int DATABASE_VERSION = 3;
        private static final String TRAN_ID = "tran_id";
        private static final String UID1= "student_id";
        private static final String MONTH="month";
        private static final String YEAR="year";
        private  static  final String FEES="fees";
        private static final String DATEOFPAYMENT="payment_date";


        private static final String CREATE_TABLE_PAYMENT = "CREATE TABLE " + TABLE_NAME_PAYMENT + " (" + TRAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UID1+ " INTEGER, " + MONTH+ " VARCHAR(255),"+YEAR+" varchar(255), "+DATEOFPAYMENT+" VARCHAR(255), "+FEES+" INTEGER , FOREIGN KEY(student_id) REFERENCES artist(student_id));";
        private static final String DROP_TABLE_PAYMENT = "DROP TABLE IF EXISTS " + TABLE_NAME_PAYMENT + ";";


        private Context context;

        public MySQLiteOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {

                db.execSQL(CREATE_TABLE_STUDENT);
                db.execSQL(CREATE_TABLE_PAYMENT);
            } catch (Exception e) {
                MyToastMessage.myMessage(context, "" + e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {

                db.execSQL(DROP_TABLE_STUDENT);
                db.execSQL(DROP_TABLE_PAYMENT);
                onCreate(db);
            } catch (Exception e) {
                MyToastMessage.myMessage(context, "" + e);
            }
        }
    }
}
