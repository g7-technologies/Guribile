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
import com.g7tech.guribile.HelperClasses.AppConfig;
import com.g7tech.guribile.HelperClasses.PendingRequestsAdapter;
import com.g7tech.guribile.HelperClasses.SessionManager;
import com.g7tech.guribile.Model.PendingRequests;
import com.g7tech.guribile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PendingRequestActivity extends AppCompatActivity {

    ProgressBar progressBar;
    List<PendingRequests> PendingRequestsList;
    RecyclerView recyclerView;
    public String URLL;
    SessionManager session;
    int serviceid;
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
        setContentView(R.layout.activity_pending_request);
        emptyRequest = findViewById(R.id.emptyRequest);
        serviceid = Integer.valueOf(session.getService());
        URLL = AppConfig.URL_WORKER_LIST+serviceid;

        Log.e("urlll",URLL);

        progressBar = findViewById(R.id.progress_circular);
        PendingRequestsList = new ArrayList<>();
        loadPendingRequests();
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadPendingRequests() {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            if(array.length() == 0){

                                progressBar.setVisibility(View.INVISIBLE);
                                emptyRequest.setVisibility(View.VISIBLE);
                            }
                            else{
                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject pendingRequestsList = array.getJSONObject(i);

                                    PendingRequestsList.add(new PendingRequests(
                                            pendingRequestsList.getInt("id"),
                                            pendingRequestsList.getString("title"),
                                            pendingRequestsList.getString("message"),
                                            pendingRequestsList.getString("status"),
                                            pendingRequestsList.getString("latitude"),
                                            pendingRequestsList.getString("longitude"),
                                            pendingRequestsList.getString("created_at")
                                    ));
                                }

                                PendingRequestsAdapter adapter = new PendingRequestsAdapter(PendingRequestActivity.this, PendingRequestsList);
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
