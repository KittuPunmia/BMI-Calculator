package com.kittu.bmi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Second_activity extends AppCompatActivity{
    EditText etWeight;
    SharedPreferences sp1;
    TextView tvWel;
    Spinner spnFeet;
    Spinner spnInch;
    Button btnCal;
    Button btnView;
    GoogleApiClient gac;
    Double temperature;
TextView tvLocation;
    TextView tvWeath;
    private static final int MY_PERMISSION_REQUEST_LOCATION =1;
    String temp1, add;
    SharedPreferences sp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_activity);
        etWeight = (EditText) findViewById(R.id.etWeight);
        tvWel = (TextView) findViewById(R.id.tvWel);
        spnFeet = (Spinner) findViewById(R.id.spnFeet);
        spnInch = (Spinner) findViewById(R.id.spnInch);
        btnCal = (Button) findViewById(R.id.btnCal);
        btnView = (Button) findViewById(R.id.btnView);
tvLocation=(TextView)findViewById(R.id.tvLocation);
        tvWeath=(TextView)findViewById(R.id.tvWeath);
        Intent in = getIntent();

        //String name=in.getStringExtra("name");
        final ArrayList<String> val = new ArrayList<String>();
        val.add("1");
        val.add("2");
        val.add("3");
        val.add("4");
        val.add("5");
        val.add("6");
        val.add("7");
        ArrayAdapter<String> value = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, val);
        spnFeet.setAdapter(value);

        final ArrayList<String> inches = new ArrayList<String>();
        inches.add("0");
        inches.add("1");
        inches.add("2");
        inches.add("3");
        inches.add("4");
        inches.add("5");
        inches.add("6");
        inches.add("7");
        inches.add("8");
        inches.add("9");
        inches.add("10");
        inches.add("11");
        final ArrayAdapter<String> adapinch = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, inches);
        spnInch.setAdapter(adapinch);

        sp1 = getSharedPreferences("MyP1", MODE_PRIVATE);
        String name = sp1.getString("name", "");
        tvWel.setText("Welcome " + name + "!!");
        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int weight = Integer.parseInt(etWeight.getText().toString());
                int pos = spnFeet.getSelectedItemPosition();
                String feet = val.get(pos);
                int pos1 = spnInch.getSelectedItemPosition();
                String inch = inches.get(pos1);
                Intent i = new Intent(Second_activity.this, Third_activity.class);
                i.putExtra("feet", feet);
                i.putExtra("inch", inch);
                i.putExtra("weight", weight);
                startActivity(i);
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Second_activity.this, ViewActivity.class);
                startActivity(i);
            }
        });

      if(ContextCompat.checkSelfPermission(Second_activity.this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Second_activity.this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(Second_activity.this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(Second_activity.this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);

            }}
        else
        {
            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try {
                tvLocation.setText(herelocation(location.getLatitude(),location.getLongitude()));

            }
            catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Please Check GPS ", Toast.LENGTH_SHORT).show();

            }
        }


        String pn=tvLocation.getText().toString();
        if(pn.length()!=0)
        {
            Task1 t1=new Task1();
            t1.execute("http://api.openweathermap.org/data/2.5/weather?units=metric&q="+pn+"&appid="+
                    "c6e315d09197cec231495138183954bd");

        }
        else
        {

            return;
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(Second_activity.this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        try {
                            tvLocation.setText(herelocation(location.getLatitude(), location.getLongitude()));


                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Please Enable GPS", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(this, " No Permission Granted ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }


    }//end of OnCreate Method

    class Task1 extends AsyncTask<String,Void,Double>
    {
        double temp;
        @Override
        protected Double doInBackground(String... strings) {

            String json="",line="";

            try {
                URL url=new URL(strings[0]);
                HttpURLConnection hc=(HttpURLConnection)url.openConnection();
                hc.connect();
                InputStream is=hc.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader br=new BufferedReader(isr);

                while ((line=br.readLine())!=null)
                {
                    json=json+line+"\n";

                }
                if(json!=null)
                {
                    JSONObject j=new JSONObject(json);
                    JSONObject q=j.getJSONObject("main");
                    temp=q.getDouble("temp");
                }



            } catch (Exception e) {


                Toast.makeText(Second_activity.this,""+e,Toast.LENGTH_SHORT).show();
            }

            return temp;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            tvWeath.setText("Temperature"+aDouble);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.web) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://" + "www.google.com"));
            startActivity(i);
        }

        if (item.getItemId() == R.id.about) {
            Toast.makeText(this, "App developed by Kittu", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to close this application?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.setTitle("Exit");
        alert.show();


    }

 /*   @Override
    public void onConnected(Bundle bundle) {
        Location loc = LocationServices.FusedLocationApi.getLastLocation(gac);
        Geocoder g = new Geocoder(this, Locale.ENGLISH);
        if(loc!=null)
        {
            try {
                List<Address> al = g.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                Address a = al.get(0);
                temp1=a.getLocality();
                add = a.getSubAdminArea();
                tvLocation.setText(temp1);
                ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
                if(!(activeNetworkInfo!=null&&activeNetworkInfo.isConnected()))
                {
                    Toast.makeText(this,"No internet connection",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Task t1=new Task();
                    t1.execute("http://api.openweathermap.org/data/2.5/weather?units=metric&q="+temp1+"&appid="+
                            "c6e315d09197cec231495138183954bd");

                }
            } catch (IOException e) {
                e.printStackTrace();


            }}
        else
        {

            Toast.makeText(this,"Plese enable GPS",Toast.LENGTH_SHORT).show();
        }
    }
    class Task extends AsyncTask<String,Void,Double>
    {
        double temp;


        @Override
        protected Double doInBackground(String... strings) {
            String jason="",line="";
            try
            {
                URL url=new URL(strings[0]);
                HttpURLConnection hc=(HttpURLConnection)url.openConnection();
                hc.connect();
                InputStream is=hc.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader br= new BufferedReader(isr);
                while ((line=br.readLine())!=null)
                {
                    jason=jason+ line+ "\n";
                }
                if(jason!=null)
                {
                    JSONObject j=new JSONObject(jason);
                    JSONObject q=j.getJSONObject("main");
                    temp=q.getDouble("temp");
                }

            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext()," "+e,Toast.LENGTH_SHORT).show();
            }
            return temp;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            tvWeath.setText("temp"+aDouble);
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


   */ public String herelocation(double lat,double lon) {
        String curCity = "";

        Geocoder geocoder = new Geocoder(Second_activity.this, Locale.getDefault());
        List<Address> addressList;
        try {

            addressList=geocoder.getFromLocation(lat,lon,1);
            if(addressList.size()>0)
            {
                curCity=addressList.get(0).getLocality();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return curCity;
    }

}