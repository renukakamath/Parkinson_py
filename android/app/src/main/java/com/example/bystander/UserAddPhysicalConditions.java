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

public class UserAddPhysicalConditions extends AppCompatActivity implements JsonResponse{

    EditText e1,e2,e3,e4,e5;
    Button b1;
    SharedPreferences sh;
    String bp,sugar,cholestrol,height,weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_physical_conditions);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.bp);
        e2=(EditText)findViewById(R.id.etsugar);
        e3=(EditText)findViewById(R.id.etcho);
        e4=(EditText)findViewById(R.id.etheight);
        e5=(EditText)findViewById(R.id.etweight);

        b1=(Button) findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bp=e1.getText().toString();
                sugar=e2.getText().toString();
                cholestrol=e3.getText().toString();
                height=e4.getText().toString();
                weight=e5.getText().toString();

                if(bp.equalsIgnoreCase(""))
                {
                    e1.setError("Enter your BP");
                    e1.setFocusable(true);
                }
                else if(sugar.equalsIgnoreCase(""))
                {
                    e2.setError("Enter your Sugar");
                    e2.setFocusable(true);
                }

                else if(cholestrol.equalsIgnoreCase("") )
                {
                    e3.setError("Enter your Cholestrol");
                    e3.setFocusable(true);
                }
                else if(height.equalsIgnoreCase(""))
                {
                    e4.setError("Enter your Height");
                    e4.setFocusable(true);
                }
                else if(weight.equalsIgnoreCase(""))
                {
                    e5.setError("Enter your Weight");
                    e5.setFocusable(true);
                }


                else {
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) UserAddPhysicalConditions.this;
                    String q ="/user_add_physical_conditions?pid="+UserViewPatients.patient_id+"&bp="+bp+"&sugar="+sugar+"&cholestrol="+cholestrol+"&height="+height+"&weight="+weight;
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
                startActivity(new Intent(getApplicationContext(), UserViewPatients.class));

            }  else {
                startActivity(new Intent(getApplicationContext(), UserViewPatients.class));

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}