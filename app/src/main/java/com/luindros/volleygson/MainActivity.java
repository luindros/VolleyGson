package com.luindros.volleygson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Gson gson;
    private List<Fruit> fruits;
    private String JsonURL = "https://raw.githubusercontent.com/fmtvp/recruit-test-data/master/data.json";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fruits=new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        requestFruits();
    }

    private void requestFruits() {

        JsonObjectRequest rq=new JsonObjectRequest(Request.Method.GET, JsonURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray fruitArray= null;
                try {
                    fruitArray=response.getJSONArray("fruit");
                    for(int i=0; i<fruitArray.length(); i++){

                        //People people= gson.fromJson(jsonObject.getJSONObject("data").toString(), People.class);
                        Fruit fruit=gson.fromJson(response.getJSONArray("fruit").get(i).toString(), Fruit.class);
                        fruits.add(fruit);
                        Toast.makeText(MainActivity.this, "Size of fruits"+fruits.size(), Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(MainActivity.this, "Type of fruits[2]"+fruits.get(2).getType(), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(rq);
    }
}
