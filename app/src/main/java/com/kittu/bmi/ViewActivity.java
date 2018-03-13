package com.kittu.bmi;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
TextView tv1;
    DbHandler db;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        tv1=(TextView)findViewById(R.id.tv1);



        tv1.setTextColor(Color.parseColor("#280707"));
        db=new DbHandler(this);

        ArrayList<String> a= new ArrayList<String>();
        a=db.getAllRecords();

        if(a.size()==0)
        {
            tv1.setText("No Records");
        }
        else {
            for (String m : a) {
                tv1.setText(tv1.getText() + "\n" + m.toString());
            }
        }
/*
        tv1.setText("");
        ArrayList<String> a=new ArrayList<String>();
        a=db.viewHis();
        if(a.size()==0)
        {
            tv1.setText("No Records");
        }
        else
        {
            for(String m:a)
            {
                tv1.setText(tv1.getText()+"\n"+m);
            }
        }*/
    }
}
