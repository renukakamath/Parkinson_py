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

public class UserViewSchedule extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] date,sch_id,stime,etime,value;
    public static String schedule_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_schedule);
        l1=(ListView)findViewById(R.id.lvview);

        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) UserViewSchedule.this;
        String q = "/userview_schedule?doc_id="+UserViewDoctors.doctor_id;
        q = q.replace(" ", "%20");
        JR.execute(q);
    }


    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            if (method.equalsIgnoreCase("userview_schedule")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    date = new String[ja1.length()];
                    stime = new String[ja1.length()];
                    etime = new String[ja1.length()];
                    sch_id = new String[ja1.length()];
                    value = new String[ja1.length()];


                    for (int i = 0; i < ja1.length(); i++) {
                        date[i] = ja1.getJSONObject(i).getString("date");
                        stime[i] = ja1.getJSONObject(i).getString("start_time");
                        etime[i] = ja1.getJSONObject(i).getString("end_time");
                        sch_id[i] = ja1.getJSONObject(i).getString("schedule_id");


//                    name[i] = ja1.getJSONObject(i).getString("fname")+" "+ja1.getJSONObject(i).getString("lname");
                        value[i] = "Date: " + date[i] + "\nStart Time: " + stime[i] + "\nEnd Time: " + etime[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    l1.setAdapter(ar);
                }
            }
//            else if (method.equalsIgnoreCase("user_book"))
//            {
//                String status = jo.getString("status");
//                Log.d("pearl", status);
//
//
//                if (status.equalsIgnoreCase("success")) {
//                    Toast.makeText(getApplicationContext(), "BOOKED SUCCESSFULLY", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getApplicationContext(), UserViewSchedule.class));
//
//                } else {
//
//                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
//                }
//            }


        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        schedule_id=sch_id[i];

        final CharSequence[] items = {"Book","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UserViewSchedule.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Book"))
                {

                    startActivity(new Intent(getApplicationContext(),UserBookDoctor.class));
//                    JsonReq JR = new JsonReq();
//                    JR.json_response = (JsonResponse) UserViewSchedule.this;
//                    String q = "/user_book?sch_id="+UserViewSchedule.schedule_id;
//                    q = q.replace(" ", "%20");
//                    JR.execute(q);


                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();

    }
}