package com.example.bystander;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class PredictDisease extends AppCompatActivity implements JsonResponse{
    EditText b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16,b17,b18,b19,b20,b21,b22;
    Button bb1;
    TextView t1;
    String a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,w;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_disease);

        b1=(EditText) findViewById(R.id.etfo);
        b2=(EditText) findViewById(R.id.etfhi);
        b3=(EditText) findViewById(R.id.etflo);
        b4=(EditText) findViewById(R.id.etmdvp);
        b5=(EditText) findViewById(R.id.etjabs);
        b6=(EditText) findViewById(R.id.etrap);
        b7=(EditText) findViewById(R.id.etmppq);
        b8=(EditText) findViewById(R.id.etjddp);
        b9=(EditText) findViewById(R.id.etshim);
        b10=(EditText) findViewById(R.id.etdb);
        b11=(EditText) findViewById(R.id.etshm);
        b12=(EditText) findViewById(R.id.etapq);
        b13=(EditText) findViewById(R.id.etap);
        b14=(EditText) findViewById(R.id.etdda);
        b15=(EditText) findViewById(R.id.etnhr);
        b16=(EditText) findViewById(R.id.ethnr);
        b17=(EditText) findViewById(R.id.etrpde);
        b18=(EditText) findViewById(R.id.etd);
        b19=(EditText) findViewById(R.id.etdfa);
        b20=(EditText) findViewById(R.id.etspread1);
        b21=(EditText) findViewById(R.id.etspread2);
        b22=(EditText) findViewById(R.id.etppe);

        bb1=(Button) findViewById(R.id.button2);

        t1=(TextView) findViewById(R.id.textView6);

        bb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               a=b1.getText().toString();
               b=b2.getText().toString();
                c=b3.getText().toString();
                d=b4.getText().toString();
                e=b5.getText().toString();
                f=b6.getText().toString();
                g=b7.getText().toString();
                h=b8.getText().toString();
                i=b9.getText().toString();
                j=b10.getText().toString();
                k=b11.getText().toString();
                l=b12.getText().toString();
                m=b13.getText().toString();
                n=b14.getText().toString();
                o=b15.getText().toString();
                p=b16.getText().toString();
                q=b17.getText().toString();
                r=b18.getText().toString();
                s=b19.getText().toString();
                t=b20.getText().toString();
                u=b21.getText().toString();
                w=b22.getText().toString();

                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) PredictDisease.this;
                String qq ="/predictdisease?a="+a+"&b="+b+"&c="+c+"&d="+d+"&e="+e+"&f="+f+"&g="+g+"&h="+h+"&i="+i+"&j="+j+"&k="+k+"&l="+l+"&m="+m+"&n="+n+"&o="+o+"&p="+p+"&q="+q+"&r="+r+"&s="+s+"&t="+t+"&u="+u+"&w="+w;
                qq = qq.replace(" ","%20");
                JR.execute(qq);
            }
        });
    }

    @Override
    public void response(JSONObject jo) {

        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                String out=jo.getString("data");
                Toast.makeText(getApplicationContext(), "Successfully Predicted Output is : "+out, Toast.LENGTH_LONG).show();
                t1.setText("Predited Output is : " +out);

            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}