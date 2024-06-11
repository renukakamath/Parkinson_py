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

public class UserViewPatients extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView l1;
    String[] name,place,phone,email,hname,gender,dob,bgroup,value,pid;
    public static String patient_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_patients);
        l1=(ListView)findViewById(R.id.lvview);

        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) UserViewPatients.this;
        String q = "/userview_patients?lid="+Login.logid;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("userview_patients")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    name = new String[ja1.length()];
                    place = new String[ja1.length()];
                    phone = new String[ja1.length()];
                    email = new String[ja1.length()];
                    hname = new String[ja1.length()];
                    gender = new String[ja1.length()];
                    dob = new String[ja1.length()];
                    bgroup = new String[ja1.length()];
                    pid = new String[ja1.length()];
                    value = new String[ja1.length()];


                    for (int i = 0; i < ja1.length(); i++) {
                        name[i] = ja1.getJSONObject(i).getString("first_name")+" "+ja1.getJSONObject(i).getString("last_name");
                        place[i] = ja1.getJSONObject(i).getString("place");
                        phone[i] = ja1.getJSONObject(i).getString("phone");
                        email[i] = ja1.getJSONObject(i).getString("email");
                        hname[i] = ja1.getJSONObject(i).getString("house_name");
                        gender[i] = ja1.getJSONObject(i).getString("gender");

                        dob[i] = ja1.getJSONObject(i).getString("dob");
                        bgroup[i] = ja1.getJSONObject(i).getString("blood_group");
                        pid[i] = ja1.getJSONObject(i).getString("patient_id");


//                    name[i] = ja1.getJSONObject(i).getString("fname")+" "+ja1.getJSONObject(i).getString("lname");
                        value[i] = "Name: " + name[i] + "\nPlace: " + place[i]+"Phone: " + phone[i] + "\nEmail: " + email[i]+"\nHouse Name: " + hname[i] + "\nGender: " + gender[i]+"\nDOB: " + dob[i] + "\nBlood Group: " + bgroup[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    l1.setAdapter(ar);
                }
            }



        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        patient_id=pid[i];

        final CharSequence[] items = {"Add Physical Conditions","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UserViewPatients.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Add Physical Conditions")) {

                    startActivity(new Intent(getApplicationContext(), UserAddPhysicalConditions.class));
                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();
    }
}