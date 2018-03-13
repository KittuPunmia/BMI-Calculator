package com.kittu.bmi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText etName,etPhone,etAge;
    Button btnReg;
    RadioGroup radioGroup;
    SharedPreferences sp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName=(EditText)findViewById(R.id.etName);
        etPhone=(EditText)findViewById(R.id.etPhone);
        etAge=(EditText)findViewById(R.id.etAge);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        btnReg=(Button)findViewById(R.id.btnReg);
        sp1=getSharedPreferences("MyP1",MODE_PRIVATE);
        String name=" ";
 name=sp1.getString("name"," ");
        if(!name.equals(" "))
        {
            Intent i=new Intent(getApplicationContext(),Second_activity.class);
            startActivity(i);
            finish();
        }
        else {

            btnReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = etName.getText().toString();
                    Long phone = Long.parseLong(etPhone.getText().toString());
                    Long age = Long.parseLong(etAge.getText().toString());
                    if (name.length() == 0) {
                        Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
                        etName.requestFocus();
                        return;
                    }
                    if (etPhone.getText().toString().length() != 10) {
                        Toast.makeText(getApplicationContext(), "Please enter Valid phone no ", Toast.LENGTH_SHORT).show();
                        etPhone.requestFocus();
                        return;
                    }
                    if (etAge.getText().toString().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Please enter age", Toast.LENGTH_SHORT).show();
                        etAge.requestFocus();
                        return;
                    }

                    int id = radioGroup.getCheckedRadioButtonId();
                    RadioButton r = (RadioButton) findViewById(id);
                    String gender = r.getText().toString();
                    SharedPreferences.Editor editor = sp1.edit();
                    editor.putString("name", name);
                    editor.putLong("phone", phone);
                    editor.putLong("age", age);
                    editor.putString("gender", gender);
                    editor.commit();
                    Intent i = new Intent(getApplicationContext(), Second_activity.class);

                    startActivity(i);
                    finish();


                }
            });
        }
    }

}
