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

public class UserViewDoctors extends AppCompatActivity implements AdapterView.OnItemClickListener,JsonResponse{

    ListView l1;
    String[] name,place,phone,qualification,doc_id,value,email;
    public static String doctor_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_doctors);

        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) UserViewDoctors.this;
        String q = "/userview_doctors?dept_id="+UserViewDepartment.department_id;
        q = q.replace(" ", "%20");
        JR.execute(q);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        doctor_id=doc_id[i];

        final CharSequence[] items = {"View Schedules","Chat","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UserViewDoctors.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("View Schedules")) {

                    startActivity(new Intent(getApplicationContext(),UserViewSchedule.class));
                }
                else if (items[item].equals("Chat")) {

                    startActivity(new Intent(getApplicationContext(),Inter_chat_immigration.class));
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

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                name = new String[ja1.length()];
                place = new String[ja1.length()];
                phone = new String[ja1.length()];
                email = new String[ja1.length()];
                doc_id = new String[ja1.length()];
                qualification = new String[ja1.length()];
                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    doc_id[i] = ja1.getJSONObject(i).getString("doctor_id");
                    phone[i] = ja1.getJSONObject(i).getString("phone");
                    place[i] = ja1.getJSONObject(i).getString("place");
                    qualification[i] = ja1.getJSONObject(i).getString("qualification");
                    email[i] = ja1.getJSONObject(i).getString("email");
                    name[i] = ja1.getJSONObject(i).getString("first_name")+" "+ja1.getJSONObject(i).getString("last_name");
                    value[i] = "Name: " + name[i] + "\nPlace: " + place[i] + "\nphone: " + phone[i] + "\nEmail: " + email[i] + "\nQualification: " + qualification[i];

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