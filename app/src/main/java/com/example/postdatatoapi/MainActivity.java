package com.example.postdatatoapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ProgressBar loadingPB;
    private Button btnLogin;
    private EditText etUname, etPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingPB = findViewById(R.id.idLoadingPB);

        etUname = findViewById(R.id.etUsername);
        etPass = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                postDataUsingVolley(etUname.getText().toString(), etPass.getText().toString());
            }
        });
    }

    // this is for login
    private void postDataUsingVolley(final String uname, final String pass) {

        // url to post our data
        String url = "http://192.168.0.132:8552/";
        // loading pogress bar this is optional
        loadingPB.setVisibility(View.VISIBLE);

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // inside on response method we are
                // hiding our progress bar
                loadingPB.setVisibility(View.GONE);

                // on below line we are displaying a success toast message.
                //Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                try {
                    // on below line we are passing our response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String result = respObj.getString("result");
                    String username = respObj.getString("uname");

                    // we just toast the value we got from API, 1 for success, 0 otherwise
                    Toast.makeText(MainActivity.this, "result is " + result + ", Hi " + username, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(MainActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("uname", uname);
                params.put("pass", pass);
                // put some code for verfication that the post came from your mobile app
                params.put("login", "some-code-here");

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }
}
//    Getting Started with Android Volley :-
//
//    To integrate Volley in your Android Studio Project, you need to add the following dependency in your build.gradle file:
//        compile 'com.android.volley:volley:1.0.0'
//
//    1. Android Volley StringRequest.
//       =StringRequest is used when you want the response returned in the form of a String. You can then parse the response using Gson or JSON as per your requirement.
//
//    syntax:-  StringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener)
//        • method : Expects an argument among GET, POST, PUT, DELETE.
//        • url : URl to fetch the response at.
//        • listener : Listens for a success response. We need to implement and override the following method in here.
//        @Override
//        public void onResponse(String response){
//
//        //response parameter is of the same type as defined in the constructor.
//
//        }
//        • errorListener: Listens for an error response. We need to implement the following method in here.
//
//        @Override
//        public void onErrorResponse(VolleyError error){
//        //handle your error here. We'll look at
//        }
//
//        • A sample code snippet of a string request is given below.
//
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//        @Override
//        public void onResponse(String response) {
//        VolleyLog.wtf(response, "utf-8");
//
//        }
//        }, new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//        VolleyLog.wtf(error.getMessage(), "utf-8");
//        }
//        });
//
//        queue.add(stringRequest);
//        }}
//
//        • Parameters and headers are specified as a key-value pair in the getParams() and getHeaders() methods respectively.
//
//        Note: getParams() method is ignored when the request type is a GET. So to pass parameters we’ll need to concatenate them in the URL string as shown below.
//        String BASE_URL = "https://reqres.in";
//        String url = String.format(BASE_URL + "/api/users?page=%1$s", "2");
//
//        2. Android Volley JsonObjectRequest.
//            =JsonObjectRequest is used to send and receive JSONObject from the server. It extends the class JsonRequest.
//
//        • A sample code snippet of a JsonObjectRequest is given below.
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
//        @Override
//        public void onResponse(JSONObject response) {
//        VolleyLog.wtf(response.toString());
//
//        }
//        }, new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//        VolleyLog.wtf(error.getMessage(), "utf-8");
//        }
//        });
//        queue.add(jsonObjectRequest)
//        }}
//
//      3. Android Volley JsonArrayRequest.
//        =JsonArrayRequest is used to send and retrieve JSONArray to and from the server.
//
//       • A sample code snippet of a JsonArrayRequest is given below.
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
//        @Override
//        public void onResponse(JSONArray response) {
//
//        }
//        }, new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//        VolleyLog.wtf(error.getMessage(), "utf-8");
//
//        }
//        });
