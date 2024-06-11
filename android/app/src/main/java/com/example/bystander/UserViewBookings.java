package com.example.bystander;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserViewBookings extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] name,date,email,fee,b_id,value;
    public static String book_id,fee_amt;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_bookings);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView)findViewById(R.id.lvview);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) UserViewBookings.this;
        String q = "/userview_bookings?lid="+sh.getString("log_id","");
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
                name = new String[ja1.length()];
                email = new String[ja1.length()];
                date = new String[ja1.length()];
                fee = new String[ja1.length()];
                b_id = new String[ja1.length()];
                value = new String[ja1.length()];


                for (int i = 0; i < ja1.length(); i++) {
                    b_id[i] = ja1.getJSONObject(i).getString("book_id");
                    name[i] = ja1.getJSONObject(i).getString("first_name")+" "+ja1.getJSONObject(i).getString("last_name");
                    email[i] = ja1.getJSONObject(i).getString("email");
                    fee[i] = ja1.getJSONObject(i).getString("fee_amount");
//                    name[i] = ja1.getJSONObject(i).getString("fname")+" "+ja1.getJSONObject(i).getString("lname");
                    value[i] = "Name: " + name[i] + "\nEmail: " + email[i] + "\nDate: " + date[i] + "\nFee: " + fee[i];

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        book_id=b_id[i];
        fee_amt=fee[i];
        final CharSequence[] items = {"View Disease History","Make Payment","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UserViewBookings.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Make Payment")) {

                    startActivity(new Intent(getApplicationContext(),UserMakePayment.class));
                }

                if (items[item].equals("View Disease History")) {

                    startActivity(new Intent(getApplicationContext(),UserViewDiseaseHistory.class));
                }



                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();

    }
}