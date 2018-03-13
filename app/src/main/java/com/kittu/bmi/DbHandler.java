package com.kittu.bmi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by user on 13-01-2018.
 */

public class DbHandler extends SQLiteOpenHelper {


    SQLiteDatabase db;
    Context context;


    public DbHandler(Context context) {
        super(context, "BmiHistory", null, 1);
        this.context = context;
        db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE history(date TEXT,result TEXT,recdate DATE PRIMARY KEY)");
        Log.d("BmiHistory", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void addRecord(String date1, String bmires, String date2) {
        try {
            db.execSQL("INSERT INTO history(date,result,recdate) VALUES ('" + date1 + "','" + bmires + "','" + date2 + "')");
            Log.d("BmiHistory", "Records Inserted");
            Toast.makeText(context, "Record Inserted", Toast.LENGTH_SHORT).show();
        } catch (SQLiteConstraintException e) {
            Toast.makeText(context, "BMI For Today Has Been Recorded", Toast.LENGTH_SHORT).show();
            Log.d("BmiHistory", "BMI For Today Has Been Recorded");
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<String> getAllRecords()
    {
        String toshow1;
        Cursor cr=db.rawQuery("SELECT * FROM history",null);
        cr.moveToFirst();
        ArrayList<String> e=new ArrayList<>();

        if((cr!=null) && (cr.getCount()>0))
        {
            do {
                String date=cr.getString(0);
                String bmires=cr.getString(1);

                toshow1=date + bmires + System.lineSeparator();
                e.add(toshow1);

            }while(cr.moveToNext());
        }
        else
        {
            toshow1="No records found";
        }
        return  e;
    }

}
