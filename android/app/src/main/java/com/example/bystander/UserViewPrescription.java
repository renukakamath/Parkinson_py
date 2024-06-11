package com.example.bystander;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserViewPrescription extends AppCompatActivity implements AdapterView.OnItemClickListener,JsonResponse {
    ListView l1;
    String[] medicine,prescription,med_pre_id,value;
    public static String medicine_pre_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_prescription);

        l1=(ListView)findViewById(R.id.lvview);

        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) UserViewPrescription.this;
        String q = "/userview_prescription";
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        medicine_pre_id=med_pre_id[i];

        final CharSequence[] items = {"Forward to pharmacy","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UserViewPrescription.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Forward to pharmacy"))
                {


                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) UserViewPrescription.this;
                    String q = "/forward_to_pharmacy?med_id="+UserViewPrescription.medicine_pre_id;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("userview_prescription")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    medicine = new String[ja1.length()];
                    prescription = new String[ja1.length()];
                    med_pre_id = new String[ja1.length()];
                    value = new String[ja1.length()];


                    for (int i = 0; i < ja1.length(); i++) {
                        medicine[i] = ja1.getJSONObject(i).getString("medicine_name");
                        prescription[i] = ja1.getJSONObject(i).getString("med_pres_description");
                        med_pre_id[i] = ja1.getJSONObject(i).getString("med_pres_id");


//                    name[i] = ja1.getJSONObject(i).getString("fname")+" "+ja1.getJSONObject(i).getString("lname");
                        value[i] = "Medicine: " + medicine[i] + "\nPrescription: " + prescription[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    l1.setAdapter(ar);
                }
            }
                else if (method.equalsIgnoreCase("forward_to_pharmacy"))
            {
                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "FORWARDED", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Userhome.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }

        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}