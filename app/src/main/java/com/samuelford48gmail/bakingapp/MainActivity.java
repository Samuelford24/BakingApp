package com.samuelford48gmail.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public RecipeAdapter recipeAdapter;
    ProgressBar pb;
final static String url="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.rvMain);
        pb=findViewById(R.id.pbMainActivity);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
       recyclerView.setLayoutManager(linearLayoutManager);
      recipeAdapter = new RecipeAdapter();
        recyclerView.setAdapter(recipeAdapter);
        pb.setVisibility(View.INVISIBLE);
     getDataFromVolley();
        }
        void getDataFromVolley(){
        pb.setVisibility(View.VISIBLE);
            final RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new com.android.volley.Response.Listener<String>() {

                        public void onResponse(String response) {

                            try {

recipeAdapter.setRecipes(RecipeUtils.getRecipes(response));
pb.setVisibility(View.INVISIBLE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }

            });
            queue.add(stringRequest);
        }
    }

