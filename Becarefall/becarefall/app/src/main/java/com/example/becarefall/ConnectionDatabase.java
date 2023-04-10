package com.example.becarefall;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConnectionDatabase implements volleyCallback{


    static String url ="https://eoeeixyzjyunmdtbqgss.supabase.co/rest/v1/";
    static String curl;
    private static RequestQueue mQueue;
    static ArrayList<c_Account> c_AccountArray ;
    static ArrayList<c_User> c_UserArray ;
    static ArrayList<c_Caretaker> c_CaretakerArray;
    static ArrayList<c_FallRecord> c_FallRecordArray;
    static ArrayList<c_ContactRecord> c_ContactRecordArray;


    public void setActivity(Context context)
    {
        mQueue = Volley.newRequestQueue(context);
        c_AccountArray = new ArrayList<>();
        c_UserArray = new ArrayList<>();
        c_CaretakerArray = new ArrayList<>();
        c_FallRecordArray = new ArrayList<>();
        c_ContactRecordArray = new ArrayList<>();


    }

    public ArrayList<c_Account> selectAccount(final volleyCallback callback)
    {
        curl = url+"account?select=*";
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, curl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //Log.e("JsonArray Message", "Response is :" + response);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject accountInfo = response.getJSONObject(i);
                      //  Log.e("Specific Account Info", "Info is  :" + accountInfo);

                        String accountID = accountInfo.getString("accountid");
                        String username = accountInfo.getString("username");
                        String password = accountInfo.getString("password");
                        c_Account c_Account = new c_Account(accountID,username,password);
                        c_AccountArray.add(c_Account);
                    }
                callback.onSuccess(c_AccountArray);

                }catch (JSONException e)
                {
                    Log.e("Error Getting Specific Account Info " ,"Error is :"+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Message" ,"Error is :"+error.getMessage());
            }
        }){
            public Map<String,String> getHeaders() throws AuthFailureError{
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVvZWVpeHl6anl1bm1kdGJxZ3NzIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzI4ODIzNDcsImV4cCI6MTk4ODQ1ODM0N30.Cez8jvOUUKUP2drDLAtMpaebbs1_7JfapVOAFdpI4cU");
                return headers;
            }
        };
        mQueue.add(jsonArray);
        return c_AccountArray;
    }

    public ArrayList<c_User> selectUser(final volleyCallback callback)
    {
        curl = url+"users?select=*";
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, curl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                  //  Log.e("JsonArray Message", "Response is :" + response);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject accountInfo = response.getJSONObject(i);
                      //  Log.e("Specific Account Info", "Info is  :" + accountInfo);

                        String userID = accountInfo.getString("userid");
                        String accountID = accountInfo.getString("accountid");
                        String name = accountInfo.getString("name");
                        String phone = accountInfo.getString("phone");
                        String status = accountInfo.getString("status");
                        String address = accountInfo.getString("address");
                        c_User c_User = new c_User(userID,accountID,name,phone,status,address);
                        c_UserArray.add(c_User);
                    }
                    callback.onUserSuccess(c_UserArray);

                }catch (JSONException e)
                {
                    Log.e("Error Getting Specific Account Info " ,"Error is :"+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Message" ,"Error is :"+error.getMessage());
            }
        }){
            public Map<String,String> getHeaders() throws AuthFailureError{
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVvZWVpeHl6anl1bm1kdGJxZ3NzIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzI4ODIzNDcsImV4cCI6MTk4ODQ1ODM0N30.Cez8jvOUUKUP2drDLAtMpaebbs1_7JfapVOAFdpI4cU");
                return headers;
            }
        };
        mQueue.add(jsonArray);
        return c_UserArray;
    }

    public ArrayList<c_Caretaker> selectCaretaker(final volleyCallback callback)
    {
        curl = url+"caretaker?select=*";
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, curl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //  Log.e("JsonArray Message", "Response is :" + response);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject accountInfo = response.getJSONObject(i);
                        //  Log.e("Specific Account Info", "Info is  :" + accountInfo);

                        String caretakerID = accountInfo.getString("caretakerid");
                        String accountID = accountInfo.getString("accountid");
                        String userID = accountInfo.getString("userid");
                        String name = accountInfo.getString("name");
                        String email = accountInfo.getString("email");
                        String phone = accountInfo.getString("phone");
                        String availability = accountInfo.getString("availability");
                        String priority = accountInfo.getString("priority");
                        c_Caretaker c_Caretaker = new c_Caretaker(caretakerID,accountID,userID,name,email,phone,availability,priority);
                        c_CaretakerArray.add(c_Caretaker);
                    }
                    callback.onCaretakerSuccess(c_CaretakerArray);

                }catch (JSONException e)
                {
                    Log.e("Error Getting Specific Account Info " ,"Error is :"+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Message" ,"Error is :"+error.getMessage());
            }
        }){
            public Map<String,String> getHeaders() throws AuthFailureError{
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVvZWVpeHl6anl1bm1kdGJxZ3NzIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzI4ODIzNDcsImV4cCI6MTk4ODQ1ODM0N30.Cez8jvOUUKUP2drDLAtMpaebbs1_7JfapVOAFdpI4cU");
                return headers;
            }
        };
        mQueue.add(jsonArray);
        return c_CaretakerArray;
    }

    public ArrayList<c_FallRecord> selectFallRecordInfo(final volleyCallback callback)
    {
        curl = url+"fallrecord?select=*";
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, curl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //  Log.e("JsonArray Message", "Response is :" + response);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject accountInfo = response.getJSONObject(i);
                        //  Log.e("Specific Account Info", "Info is  :" + accountInfo);

                        String fallRecordID = accountInfo.getString("fallrecordid");
                        String userID = accountInfo.getString("userid");
                        String date = accountInfo.getString("date");
                        String time = accountInfo.getString("time");
                        String emergencyCall = accountInfo.getString("emergencycall");

                        c_FallRecord c_FallRecord = new c_FallRecord(fallRecordID,userID,date,time,emergencyCall);
                        c_FallRecordArray.add(c_FallRecord);
                    }
                    callback.onFallRecordSuccess(c_FallRecordArray);

                }catch (JSONException e)
                {
                    Log.e("Error Getting Specific Account Info " ,"Error is :"+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Message" ,"Error is :"+error.getMessage());
            }
        }){
            public Map<String,String> getHeaders() throws AuthFailureError{
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVvZWVpeHl6anl1bm1kdGJxZ3NzIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzI4ODIzNDcsImV4cCI6MTk4ODQ1ODM0N30.Cez8jvOUUKUP2drDLAtMpaebbs1_7JfapVOAFdpI4cU");
                return headers;
            }
        };
        mQueue.add(jsonArray);
        return c_FallRecordArray;
    }

    public ArrayList<c_ContactRecord> selectContactRecordInfo(final volleyCallback callback)
    {
        curl = url+"contactrecord?select=*";
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, curl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //  Log.e("JsonArray Message", "Response is :" + response);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject accountInfo = response.getJSONObject(i);
                        //  Log.e("Specific Account Info", "Info is  :" + accountInfo);

                        String contactRecordID = accountInfo.getString("contactrecordid");
                        String fallRecordID = accountInfo.getString("fallrecordid");
                        String userID = accountInfo.getString("userid");
                        String name = accountInfo.getString("name");
                        String phone = accountInfo.getString("phone");;
                        String time = accountInfo.getString("time");
                        String pickUp = accountInfo.getString("pickup");

                        c_ContactRecord c_ContactRecord = new c_ContactRecord(contactRecordID,fallRecordID,userID,name,phone,time,pickUp);
                        c_ContactRecordArray.add(c_ContactRecord);
                    }
                    callback.onContactRecordSuccess(c_ContactRecordArray);

                }catch (JSONException e)
                {
                    Log.e("Error Getting Specific Account Info " ,"Error is :"+e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Message" ,"Error is :"+error.getMessage());
            }
        }){
            public Map<String,String> getHeaders() throws AuthFailureError{
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVvZWVpeHl6anl1bm1kdGJxZ3NzIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzI4ODIzNDcsImV4cCI6MTk4ODQ1ODM0N30.Cez8jvOUUKUP2drDLAtMpaebbs1_7JfapVOAFdpI4cU");
                return headers;
            }
        };
        mQueue.add(jsonArray);
        return c_ContactRecordArray;
    }

    @Override
    public void onSuccess(final ArrayList<c_Account> accountArray) {
    }
    @Override
    public void onUserSuccess(final ArrayList<c_User> userArray) {
    }
    @Override
    public void onCaretakerSuccess(final ArrayList<c_Caretaker> caretakerArray) {
    }
    @Override
    public void onFallRecordSuccess(final ArrayList<c_FallRecord> fallRecordArray) {
    }
    @Override
    public void onContactRecordSuccess(final ArrayList<c_ContactRecord> contactRecordArray) {
    }

    public void insertAccountInfo(c_Account user_Account)
    {
        Log.e("check user_Account Info for Sign up ","ID"+user_Account.getAccountID()+" username:"+user_Account.getUsername()+ " password"+user_Account.getPassword());
        curl = url + "account";
        try
        {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("accountid",user_Account.getAccountID() );
            jsonBody.put("username", user_Account.getUsername());
            jsonBody.put("password", user_Account.getPassword());


            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, curl, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                        Log.e("insert Account Response ","Response:"+response );
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("Error Insert Account Info " ,"Error is :"+error.getMessage()+":");
                }
            }){
                public Map<String,String> getHeaders() throws AuthFailureError{
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVvZWVpeHl6anl1bm1kdGJxZ3NzIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzI4ODIzNDcsImV4cCI6MTk4ODQ1ODM0N30.Cez8jvOUUKUP2drDLAtMpaebbs1_7JfapVOAFdpI4cU");
                    headers.put("Content-Type","application/json");
                    headers.put("Prefer","return=minimal");

                    return headers;
                }
            };
            mQueue.add(stringRequest);
         } catch (JSONException e) {
             e.printStackTrace();
         }

    }

    public void insertUserInfo(c_User user_User)
    {
        Log.e("check user_User Info for Sign up ","ID"+user_User.getAccountID()+" Name:"+user_User.getName()+ " Phone:"+user_User.getPhoneNumber());
        curl = url + "users";
        try
        {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("userid",user_User.getUserID() );
            jsonBody.put("accountid", user_User.getAccountID());
            jsonBody.put("name", user_User.getName());
            jsonBody.put("phone",user_User.getPhoneNumber() );
            jsonBody.put("status", user_User.getStatus());
            jsonBody.put("address", user_User.getAddress());

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, curl, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("insert User Response ","Response:"+response );
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("Error Insert User Info " ,"Error is :"+error.getMessage()+":");
                }
            }){
                public Map<String,String> getHeaders() throws AuthFailureError{
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVvZWVpeHl6anl1bm1kdGJxZ3NzIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzI4ODIzNDcsImV4cCI6MTk4ODQ1ODM0N30.Cez8jvOUUKUP2drDLAtMpaebbs1_7JfapVOAFdpI4cU");
                    headers.put("Content-Type","application/json");
                    headers.put("Prefer","return=minimal");

                    return headers;
                }
            };
            mQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void insertFallRecordInfo(c_FallRecord user_FallRecord)
    {
        Log.e("check user_FallRecord Info  ","fallRecordID:" +user_FallRecord.getFallRecordID()+" userID:"+user_FallRecord.getUserID()+" username:"+user_FallRecord.getDate()+ " getEmergencyCall:"+user_FallRecord.getEmergencyCall());
        curl = url + "fallrecord";
        try
        {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("fallrecordid",user_FallRecord.getFallRecordID() );
            jsonBody.put("userid", user_FallRecord.getUserID());
            jsonBody.put("date", user_FallRecord.getDate());
            jsonBody.put("time", user_FallRecord.getTime());
            jsonBody.put("emergencycall", user_FallRecord.getEmergencyCall());


            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, curl, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("insert  Fall Record Response ","Response:"+response );
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("Error Insert Fall Record Info " ,"Error is :"+error.getMessage()+":");
                }
            }){
                public Map<String,String> getHeaders() throws AuthFailureError{
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVvZWVpeHl6anl1bm1kdGJxZ3NzIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzI4ODIzNDcsImV4cCI6MTk4ODQ1ODM0N30.Cez8jvOUUKUP2drDLAtMpaebbs1_7JfapVOAFdpI4cU");
                    headers.put("Content-Type","application/json");
                    headers.put("Prefer","return=minimal");

                    return headers;
                }
            };
            mQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void insertContactRecordInfo(c_ContactRecord user_ContactRecord)
    {
        Log.e("check user_ContactRecord Info  ","ContactRecordID:" +user_ContactRecord.getFallRecordID()+" userID:"+user_ContactRecord.getFallRecordID()+" userID:"+user_ContactRecord.getUserID());
        curl = url + "contactrecord";
        try
        {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("contactrecordid",user_ContactRecord.getContactRecordID() );
            jsonBody.put("fallrecordid", user_ContactRecord.getFallRecordID());
            jsonBody.put("userid",user_ContactRecord.getUserID());
            jsonBody.put("name", user_ContactRecord.getName());
            jsonBody.put("phone", user_ContactRecord.getPhone());
            jsonBody.put("time", user_ContactRecord.getTime());
            jsonBody.put("pickup", user_ContactRecord.getPickUp());


            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, curl, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("insert  Fall Record Response ","Response:"+response );
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("Error Insert Fall Record Info " ,"Error is :"+error.getMessage()+":");
                }
            }){
                public Map<String,String> getHeaders() throws AuthFailureError{
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVvZWVpeHl6anl1bm1kdGJxZ3NzIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzI4ODIzNDcsImV4cCI6MTk4ODQ1ODM0N30.Cez8jvOUUKUP2drDLAtMpaebbs1_7JfapVOAFdpI4cU");
                    headers.put("Content-Type","application/json");
                    headers.put("Prefer","return=minimal");

                    return headers;
                }
            };
            mQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void updateUserInfo(c_User user_User)
    {
        Log.e("check user_User Info for Update ","ID"+user_User.getAccountID()+" Name:"+user_User.getName()+ " Phone:"+user_User.getPhoneNumber());
        curl = url + "users?userid=eq."+user_User.getUserID();
        Log.e("check curl Info for Update ","curl:"+curl);
        try
        {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("userid",user_User.getUserID() );
            jsonBody.put("accountid", user_User.getAccountID());
            jsonBody.put("name", user_User.getName());
            jsonBody.put("phone",user_User.getPhoneNumber() );
            jsonBody.put("status", user_User.getStatus());
            jsonBody.put("address", user_User.getAddress());

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PUT, curl, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("insert User Response ","Response:"+response );
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("Error Insert User Info " ,"Error is :"+error.getMessage()+":");
                }
            }){
                public Map<String,String> getHeaders() throws AuthFailureError{
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVvZWVpeHl6anl1bm1kdGJxZ3NzIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzI4ODIzNDcsImV4cCI6MTk4ODQ1ODM0N30.Cez8jvOUUKUP2drDLAtMpaebbs1_7JfapVOAFdpI4cU");
                    headers.put("Content-Type","application/json");
                    headers.put("Prefer","return=minimal");

                    return headers;
                }
            };
            mQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void updateCaretakerInfo(c_Caretaker user_Caretaker)
    {
        Log.e("check user_Caretaker Info for Update ","ID:"+user_Caretaker.getAccountID()+" Name:"+user_Caretaker.getName()+ " Phone:"+user_Caretaker.getPhone());
        curl = url + "caretaker?caretakerid=eq."+user_Caretaker.getCaretakerID();
        Log.e("check curl Info for Update ","curl:"+curl);
        try
        {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("caretakerid", user_Caretaker.getCaretakerID());
            jsonBody.put("accountid", user_Caretaker.getAccountID());
            jsonBody.put("userid",user_Caretaker.getUserID() );
            jsonBody.put("name", user_Caretaker.getName());
            jsonBody.put("email", user_Caretaker.getEmail());
            jsonBody.put("phone",user_Caretaker.getPhone() );
            jsonBody.put("availability", user_Caretaker.getAvailability());
            jsonBody.put("priority", user_Caretaker.getPriority());

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PUT, curl, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("insert User Response ","Response:"+response );
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("Error Insert User Info " ,"Error is :"+error.getMessage()+":");
                }
            }){
                public Map<String,String> getHeaders() throws AuthFailureError{
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVvZWVpeHl6anl1bm1kdGJxZ3NzIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzI4ODIzNDcsImV4cCI6MTk4ODQ1ODM0N30.Cez8jvOUUKUP2drDLAtMpaebbs1_7JfapVOAFdpI4cU");
                    headers.put("Content-Type","application/json");
                    headers.put("Prefer","return=minimal");

                    return headers;
                }
            };
            mQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void updateAccountInfo(c_Account user_Account)
    {
        Log.e("Update Account Info ","ID"+user_Account.getAccountID()+" username:"+user_Account.getUsername()+ " password"+user_Account.getPassword());
        curl = url + "account?accountid=eq."+user_Account.getAccountID();
        try
        {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("accountid",user_Account.getAccountID() );
            jsonBody.put("username", user_Account.getUsername());
            jsonBody.put("password", user_Account.getPassword());


            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PUT, curl, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("insert Account Response ","Response:"+response );
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("Error Insert Account Info " ,"Error is :"+error.getMessage()+":");
                }
            }){
                public Map<String,String> getHeaders() throws AuthFailureError{
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVvZWVpeHl6anl1bm1kdGJxZ3NzIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzI4ODIzNDcsImV4cCI6MTk4ODQ1ODM0N30.Cez8jvOUUKUP2drDLAtMpaebbs1_7JfapVOAFdpI4cU");
                    headers.put("Content-Type","application/json");
                    headers.put("Prefer","return=minimal");

                    return headers;
                }
            };
            mQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

interface volleyCallback{
    void onSuccess(ArrayList<c_Account> accountArray);
    void onUserSuccess(ArrayList<c_User> userArray);
    void onCaretakerSuccess(ArrayList<c_Caretaker> caretakerArray);
    void onFallRecordSuccess(ArrayList<c_FallRecord> fallRecordArray);
    void onContactRecordSuccess(ArrayList<c_ContactRecord> contactRecordArray);
}
