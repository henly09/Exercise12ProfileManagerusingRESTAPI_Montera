package com.hcdc.exercise12profilemanagerusingrestapi_montera;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CreateDataActivity extends AppCompatActivity {

    EditText nameregistermain,ageregistermain;
    Spinner genderregistermain;
    Button insertmain,cancelmain;
    RequestQueue requestQueue;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_data);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("CC106_MONTERA_EXER_12");

        nameregistermain = findViewById(R.id.nameregister);
        ageregistermain = findViewById(R.id.ageregister);
        genderregistermain = findViewById(R.id.genderregister);
        insertmain = findViewById(R.id.insert);
        cancelmain = findViewById(R.id.cancel);

        insertmain.setOnClickListener(view ->{
            try{
                insert(nameregistermain.getText().toString(),ageregistermain.getText().toString(),genderregistermain.getSelectedItem().toString());
                Intent intent = new Intent(CreateDataActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(CreateDataActivity.this, "Successfully Inserted", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e){
                Toast.makeText(this, "Failed to Insert", Toast.LENGTH_SHORT).show();
            }
        });

        cancelmain.setOnClickListener(view ->{
            Intent intent = new Intent(CreateDataActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(CreateDataActivity.this, "Dashboard", Toast.LENGTH_SHORT).show();
            finish();
        });

    }

    public void insert(String name,String age, String gender){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest = new StringRequest(
                Request.Method.POST,
                "https://192.168.1.9/exer12/insertdata.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")){
                            Toast.makeText(
                                    getApplicationContext(),
                                    response,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                    map.put("name", name);
                    map.put("age", age);
                    map.put("gender", gender);
                    return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}