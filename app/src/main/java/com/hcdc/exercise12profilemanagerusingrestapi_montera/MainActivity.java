package com.hcdc.exercise12profilemanagerusingrestapi_montera;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<DataModel> datamodelmain;
    RecyclerView rviewmain;
    Button createmain,refreshmain;
    RequestQueue requestQueue;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("CC106_MONTERA_EXER_12");
        rviewmain = findViewById(R.id.rview);
        createmain = findViewById(R.id.create);
        refreshmain = findViewById(R.id.refresh);
        datamodelmain = new ArrayList<>();
        loadinfo();
        ssl.nuke();

        createmain.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateDataActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Create Information", Toast.LENGTH_SHORT).show();
            finish();
        });

        refreshmain.setOnClickListener(view -> {
            loadinfo();
            Toast.makeText(MainActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();
        });

    }

    public void loadinfo(){
        datamodelmain.clear();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest = new StringRequest(
                Request.Method.GET,
                "http://192.168.1.9/exer12/display.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                datamodelmain.add(
                                        new DataModel(
                                                jsonObject.getInt("id"),
                                                jsonObject.getString("name"),
                                                jsonObject.getInt("age"),
                                                jsonObject.getString("gender")
                                        )
                                );
                            }
                            LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            rviewmain.setLayoutManager(layoutManager);
                            rviewmain.setAdapter(new DataAdapter(datamodelmain,MainActivity.this));
                            Log.v("JSON Array:", jsonArray.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue.add(stringRequest);
    }


}