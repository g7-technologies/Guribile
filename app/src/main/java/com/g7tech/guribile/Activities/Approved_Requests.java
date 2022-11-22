package com.g7tech.guribile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.g7tech.guribile.HelperClasses.AcceptedRequestsAdapter;
import com.g7tech.guribile.HelperClasses.AppConfig;
import com.g7tech.guribile.HelperClasses.SessionManager;
import com.g7tech.guribile.Model.ApprovedRequests;
import com.g7tech.guribile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Approved_Requests extends AppCompatActivity {

    ProgressBar progressBar;
    List<ApprovedRequests> ApprovedRequestsList;
    RecyclerView recyclerView;
    public String URLL;
    SessionManager session;
    TextView emptyRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(getApplicationContext());
        if (session.getLanguage().length() == 6){
            setApplocale("som");
        }
        else {
            setApplocale("en");
        }
        setContentView(R.layout.activity_approved__requests);
        emptyRequest = findViewById(R.id.emptyRequest);

        URLL = AppConfig.URL_ACCEPTED_WORK_LIST+session.getId();
        Log.e("urlll",URLL);

        progressBar = findViewById(R.id.progress_circular);
        ApprovedRequestsList = new ArrayList<>();
        loadApprovedRequests();
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadApprovedRequests() {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            Log.e("response",response);
                            if(array.length() == 0){

                                progressBar.setVisibility(View.INVISIBLE);
                                emptyRequest.setVisibility(View.VISIBLE);
                            }
                            else{

                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject approvedRequestsList = array.getJSONObject(i);

                                    ApprovedRequestsList.add(new ApprovedRequests(
                                            approvedRequestsList.getInt("id"),
                                            approvedRequestsList.getString("title"),
                                            approvedRequestsList.getString("message"),
                                            approvedRequestsList.getString("status"),
                                            approvedRequestsList.getString("created_at")
                                    ));
                                }
                                AcceptedRequestsAdapter adapter = new AcceptedRequestsAdapter(Approved_Requests.this, ApprovedRequestsList);
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView.setAdapter(adapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void setApplocale(String localeCode){
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            conf.setLocale(new Locale(localeCode.toLowerCase()));
        }else{
            conf.locale = new Locale(localeCode.toLowerCase());
        }
        res.updateConfiguration(conf,dm);
    }


}
