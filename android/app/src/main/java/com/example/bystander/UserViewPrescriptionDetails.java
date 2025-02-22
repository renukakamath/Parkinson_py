package com.example.bystander;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserViewPrescriptionDetails extends AppCompatActivity implements JsonResponse{

    ListView l1;
    String[] medicine,prescription,date,value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_prescription_details);

        l1=(ListView)findViewById(R.id.lvview);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) UserViewPrescriptionDetails.this;
        String q = "/userview_prescriptions?bid="+UserViewBookings.book_id;
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
                medicine = new String[ja1.length()];
                prescription = new String[ja1.length()];
                date = new String[ja1.length()];


                for (int i = 0; i < ja1.length(); i++) {
                    medicine[i] = ja1.getJSONObject(i).getString("medicine");
                    prescription[i] = ja1.getJSONObject(i).getString("med_pres_description");
                    date[i] = ja1.getJSONObject(i).getString("date_time");

//                    name[i] = ja1.getJSONObject(i).getString("fname")+" "+ja1.getJSONObject(i).getString("lname");
                    value[i] = "Medicine: " + medicine[i] + "\nPrescription: " + prescription[i] + "\nDate: " + date[i] ;

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