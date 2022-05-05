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
import android.widget.TextView;
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

public class EditActivity extends AppCompatActivity {

    EditText nameeditmain,ageeditmain;
    Spinner gendereditmain;
    Button editmain,cancelmain;
    TextView ideditmain;
    RequestQueue requestQueue;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("CC106_MONTERA_EXER_12");

        ideditmain = findViewById(R.id.idedit);
        nameeditmain = findViewById(R.id.nameedit);
        ageeditmain = findViewById(R.id.ageedit);
        gendereditmain = findViewById(R.id.genderedit);
        editmain = findViewById(R.id.edit);
        cancelmain = findViewById(R.id.canceledit);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        nameeditmain.setText(intent.getStringExtra("name"));
        ageeditmain.setText(intent.getStringExtra("age"));
        ideditmain.setText(id);

        editmain.setOnClickListener(view ->{
            try {
                if(gendereditmain != null && ageeditmain != null && nameeditmain != null) {
                    edit(id, nameeditmain.getText().toString(), ageeditmain.getText().toString(), gendereditmain.getSelectedItem().toString());
                    Intent intent3 = new Intent(EditActivity.this, MainActivity.class);
                    startActivity(intent3);
                    Toast.makeText(EditActivity.this, "Successfully Edited", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e){
                Toast.makeText(this, "Error Occurred, Please Try Again Later..", Toast.LENGTH_SHORT).show();
            }
        });

        cancelmain.setOnClickListener(view ->{
            Intent intent2 = new Intent(EditActivity.this, MainActivity.class);
            startActivity(intent2);
            Toast.makeText(EditActivity.this, "Dashboard", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    public void edit(String id,String name, String age, String gender){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest = new StringRequest(
                Request.Method.POST,
                "https://192.168.1.9/exer12/edit.php",
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
                map.put("id", id);
                map.put("name", name);
                map.put("age", age);
                map.put("gender", gender);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}