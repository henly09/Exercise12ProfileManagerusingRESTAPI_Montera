package com.hcdc.exercise12profilemanagerusingrestapi_montera;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataView> {

    private ArrayList<DataModel> modelArrayList;
    private Context context;
    RequestQueue requestQueue;
    StringRequest stringRequest;

    public DataAdapter(ArrayList<DataModel> modelArrayList,Context context){
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DataAdapter.DataView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.data_adapt, parent, false);
        return new DataView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.DataView holder, int position) {
        DataModel model = modelArrayList.get(position);
        holder.id.setText("ID: "+Integer.toString(model.getId()));
        holder.name.setText("Name: "+model.getName());
        holder.age.setText("Age: "+Integer.toString(model.getAge()));
        holder.gender.setText("Gender: "+model.getGender());

        holder.edit.setOnClickListener(view -> {
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra("id", Integer.toString(model.getId()));
            intent.putExtra("name", model.getName());
            intent.putExtra("age", Integer.toString(model.getAge()));
            intent.putExtra("gender", model.getGender());
            context.startActivity(intent);
            Toast.makeText(context, model.getName(), Toast.LENGTH_SHORT).show();
        });

        holder.delete.setOnClickListener(view -> {
            requestQueue = Volley.newRequestQueue(context);
            stringRequest = new StringRequest(
                    Request.Method.POST,
                    "https://192.168.1.9/exer12/delete.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")){
                                Toast.makeText(
                                        context,
                                        response,
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(
                                    context,
                                    error.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("id", Integer.toString(model.getId()));
                    return map;
                }
            };
            requestQueue.add(stringRequest);
        });
        }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class DataView extends RecyclerView.ViewHolder {

        TextView id,name,age,gender;
        Button edit,delete;

        public DataView(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.idnumberdisplay);
            name = itemView.findViewById(R.id.namedisplay);
            age = itemView.findViewById(R.id.agedisplay);
            gender = itemView.findViewById(R.id.genderdisplay);
            edit = itemView.findViewById(R.id.editbutton);
            delete = itemView.findViewById(R.id.deletebutton);
        }
    }
}
