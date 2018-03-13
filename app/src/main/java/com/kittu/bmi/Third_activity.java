package com.kittu.bmi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import java.util.Calendar;
import android.net.Uri;
import android.os.Build;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Third_activity extends AppCompatActivity {
    TextView tvRes;
    TextView tvUnder,tvNorm,tvOver,tvObese;
    Button btnBack,btnShare,btnSave;
SharedPreferences sp1;
    DbHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_activity);

        Intent i=getIntent();
        String feet= i.getStringExtra("feet");
        //int feet=i.getIntExtra("feet");
        String inch=i.getStringExtra("inch");
        final int weight=i.getIntExtra("weight",0);
        int f=Integer.parseInt(feet);
        int in=Integer.parseInt(inch);
        double m1=in*0.0254;
        double m2=f*0.305;
        double height=m1+m2;
        final double res=weight/(height*height);

        tvRes=(TextView)findViewById(R.id.tvRes);
        tvUnder=(TextView)findViewById(R.id.tvUnder);
        tvNorm=(TextView)findViewById(R.id.tvNorm);
        tvOver=(TextView)findViewById(R.id.tvOver);
        tvObese=(TextView)findViewById(R.id.tvObese);
        btnBack=(Button)findViewById(R.id.btnback);
        btnShare=(Button)findViewById(R.id.btnShare);
        btnSave=(Button)findViewById(R.id.btnSave);
        db=new DbHandler(this);
sp1=getSharedPreferences("MyP1",MODE_PRIVATE);
        if (res<=18.5)
        {
           tvRes.setText("Your BMI is"+res+"and You are UnderWeight");
            tvUnder.setTextColor(Color.rgb(200,0,0));        }
        else if(res<=25) {
            tvRes.setText("Your BMI is"+res+"and You are Normal");
            tvNorm.setTextColor(Color.rgb(200,0,0));

        }
        else if(res<=30) {
            tvRes.setText("Your BMI is"+res+"and You are Over Weight");
            tvOver.setTextColor(Color.rgb(200,0,0));

        }
        else {
            tvRes.setText("Your BMI is"+res+"and You are Obese");
            tvObese.setTextColor(Color.rgb(200,0,0));

        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String n=sp1.getString("name","");
                Long  age=sp1.getLong("age",0);
                Long phone=sp1.getLong("phone",0);
                String gender=sp1.getString("gender","M");
                String OUT=tvRes.getText().toString();
                String email="kittu97punmia@gmail.com";
                Intent em=new Intent(Intent.ACTION_SENDTO);
                String msg="Name:"+n+"\nAge:"+age+"\nContact:"+phone+"\nGender:"+gender+"\n"+OUT+"";
                em.setData(Uri.parse("mailto:"+email));
                em.putExtra(Intent.EXTRA_SUBJECT,"BMI REPORT");
                em.putExtra(Intent.EXTRA_TEXT,msg);
                startActivity(em);

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar
                        .getInstance().getTime());
                String finalbmi= " Bmi is: " + res + " Weight: " + weight ;
                Date today=new Date();
                String t="Date: " + today.toString();
                db.addRecord(t,finalbmi,date);
/*
                String date=new SimpleDateFormat("YY-MM-DD").format(Calendar.getInstance().getTime());
                String finalBmi="BMI is:"+res+"Weight is:"+weight;
                long rid=db.addRecord(date,finalBmi);
                if(rid>0)
                {
                    Toast.makeText(getApplicationContext(),"RECORD INSERTED",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"RECORD no",Toast.LENGTH_SHORT).show();

                }*/
            }
        });




        }
}

