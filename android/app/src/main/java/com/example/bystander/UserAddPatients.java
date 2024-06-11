package com.example.bystander;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class UserAddPatients extends AppCompatActivity implements JsonResponse{

    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
    Button b1;
    SharedPreferences sh;
    String fname,lname,place,phone,email,hname,gen,dob,bgroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_patients);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.etfname);
        e2=(EditText)findViewById(R.id.etlname);

        e3=(EditText)findViewById(R.id.etplace);
        e4=(EditText)findViewById(R.id.etphone);
        e5=(EditText)findViewById(R.id.etemail);
        e6=(EditText)findViewById(R.id.hno);
        e7=(EditText)findViewById(R.id.gen);
        e8=(EditText)findViewById(R.id.dob);
        e9=(EditText)findViewById(R.id.bgroup);



        b1=(Button) findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname=e1.getText().toString();
                lname=e2.getText().toString();
                place=e3.getText().toString();
                phone=e4.getText().toString();
                email=e5.getText().toString();

                hname=e6.getText().toString();
                gen=e7.getText().toString();
                dob=e8.getText().toString();
                bgroup=e9.getText().toString();





                if(fname.equalsIgnoreCase("")|| !fname.matches("[a-zA-Z ]+"))
                {
                    e1.setError("Enter your firstname");
                    e1.setFocusable(true);
                }
                else if(lname.equalsIgnoreCase("") || !lname.matches("[a-zA-Z ]+"))
                {
                    e2.setError("Enter your lastname");
                    e2.setFocusable(true);
                }

                else if(place.equalsIgnoreCase("") || !place.matches("[a-zA-Z ]+"))
                {
                    e3.setError("Enter your place");
                    e3.setFocusable(true);
                }
                else if(phone.equalsIgnoreCase("") || phone.length()!=10)
                {
                    e4.setError("Enter your phone");
                    e4.setFocusable(true);
                }
                else if(email.equalsIgnoreCase("") || !email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+"))
                {
                    e5.setError("Enter your email");
                    e5.setFocusable(true);
                }
                else if(hname.equalsIgnoreCase(""))
                {
                    e6.setError("Enter your Housename");
                    e6.setFocusable(true);
                }
                else if(gen.equalsIgnoreCase(""))
                {
                    e7.setError("Enter your Gender");
                    e7.setFocusable(true);
                }
                else if(dob.equalsIgnoreCase(""))
                {
                    e8.setError("Enter your DOB");
                    e8.setFocusable(true);
                }
                else if(bgroup.equalsIgnoreCase(""))
                {
                    e9.setError("Enter your Blood Group");
                    e9.setFocusable(true);
                }

                else {
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) UserAddPatients.this;
                    String q ="/user_manage_patients?fname="+fname+"&lname="+lname+"&place="+place+"&phone="+phone+"&email="+email+"&hname="+hname+"&dob="+dob+"&bgroup="+bgroup+"&gen="+gen+"&lid="+sh.getString("log_id","");
                    q = q.replace(" ","%20");
                    JR.execute(q);
                }
            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), UserAddPatients.class));

            }  else {
                startActivity(new Intent(getApplicationContext(), UserAddPatients.class));

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}