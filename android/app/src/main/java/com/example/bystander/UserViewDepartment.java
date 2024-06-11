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

public class UserViewDepartment extends AppCompatActivity implements AdapterView.OnItemClickListener,JsonResponse {

    ListView l1;
    String[] department,dept_id,value;
    public static String department_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_department);

        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) UserViewDepartment.this;
        String q = "/userview_dept";
        q = q.replace(" ", "%20");
        JR.execute(q);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        department_id=dept_id[i];

        final CharSequence[] items = {"View Doctors","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UserViewDepartment.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("View Doctors")) {

                    startActivity(new Intent(getApplicationContext(),UserViewDoctors.class));
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
                dept_id = new String[ja1.length()];
                department = new String[ja1.length()];


                for (int i = 0; i < ja1.length(); i++) {
                    dept_id[i] = ja1.getJSONObject(i).getString("department_id");
                    department[i] = ja1.getJSONObject(i).getString("department");

//                    name[i] = ja1.getJSONObject(i).getString("fname")+" "+ja1.getJSONObject(i).getString("lname");
//                    value[i] = "Name: " + name[i] + "\nPlace: " + place[i] + "\nphone: " + phone[i] + "\nEmail: " + email[i];

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, department);
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