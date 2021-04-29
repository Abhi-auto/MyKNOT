package com.example.myknot;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

class singleTon {
    private static singleTon mInstance;
    private RequestQueue requestQueue;

    private singleTon(Context context){
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized singleTon getInstance(Context context){
        if(mInstance == null){
            mInstance = new singleTon(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}