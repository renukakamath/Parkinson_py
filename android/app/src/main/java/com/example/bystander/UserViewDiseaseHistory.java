package com.example.bystander;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserViewDiseaseHistory extends AppCompatActivity implements JsonResponse{
    ListView l1;
    String[] description,date,value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_disease_history);

        l1=(ListView)findViewById(R.id.lvview);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) UserViewDiseaseHistory.this;
        String q = "/userview_desease_history?bid="+UserViewBookings.book_id;
        q = q.replace(" ", "%20");
        JR.execute(q);


    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                description = new String[ja1.length()];
                date = new String[ja1.length()];


                for (int i = 0; i < ja1.length(); i++) {
                    description[i] = ja1.getJSONObject(i).getString("description");
                    date[i] = ja1.getJSONObject(i).getString("date_time");

            //        name[i] = ja1.getJSONObject(i).getString("fname")+" "+ja1.getJSONObject(i).getString("lname");
                    value[i] = "Description: " + description[i] + "\nDate: " + date[i];

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
            }
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
}