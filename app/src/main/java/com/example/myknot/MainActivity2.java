package com.example.myknot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.r0adkll.slidr.Slidr;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends Activity {
    TextView title;
    Button success;
    ImageView image;
    String successURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Slidr.attach(this);

        title = findViewById(R.id.title);
        success = findViewById(R.id.success);
        image = findViewById(R.id.image);
        showDailogBox(this,singleTon.getInstance(this).getRequestQueue());
    }

    void showDailogBox (Context context , RequestQueue queue) {
        String url = "https://backend-test-zypher.herokuapp.com/testData";
        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String titleString = response.getString("title");
                            Log.d("stringRequest", titleString);
                            String imageURL = response.getString("imageURL");
                            successURL = response.getString("success_url");
                            title.setText(titleString);
                            Glide.with(context).load(imageURL).into(image);
                            success.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d("OnClick ", "onCLick invoked");
                                    Intent urlintent = new Intent();
                                    urlintent.setData(Uri.parse(successURL));
                                    urlintent.setAction(Intent.ACTION_VIEW);
                                    context.startActivity(urlintent);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Inside onError Response", "Something wrong occurs!!");
            }
        });
        //Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}