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

public class UserBookDoctor extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] fname, lname, gender, p_id, value;
    public static String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_doctor);
        l1 = (ListView) findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) UserBookDoctor.this;
        String q = "/patient?userid="+Login.logid;
        q = q.replace(" ", "%20");
        JR.execute(q);

    }


    @Override
    public void response(JSONObject jo) {
        try {
            String method = jo.getString("method");

            if (method.equalsIgnoreCase("patient")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    fname = new String[ja1.length()];
                    lname = new String[ja1.length()];
                    gender = new String[ja1.length()];

                    p_id = new String[ja1.length()];

                    value = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        fname[i] = ja1.getJSONObject(i).getString("first_name");
                        lname[i] = ja1.getJSONObject(i).getString("last_name");
                        gender[i] = ja1.getJSONObject(i).getString("gender");
                        p_id[i] = ja1.getJSONObject(i).getString("patient_id");


                        value[i] = "First Name: " + fname[i] + "\nLast Name: " + lname[i] + "\nGender: " + gender[i] + "\n";

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    l1.setAdapter(ar);
                }} else if (method.equalsIgnoreCase("bookdoctor")) {
                    String status = jo.getString("status");
                    Log.d("pearl", status);


                    if (status.equalsIgnoreCase("success")) {
                        Toast.makeText(getApplicationContext(), "BOOKED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), UserViewSchedule.class));

                    } else {

                        Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                    }
                }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pid=p_id[i];

        final CharSequence[] items = {"Book","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UserBookDoctor.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Book")) {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) UserBookDoctor.this;
                    String q = "/bookdoctor?userid="+Login.logid+"&patient="+pid+"&schedule="+UserViewSchedule.schedule_id;
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
}

