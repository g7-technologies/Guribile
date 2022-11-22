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
import com.g7tech.guribile.HelperClasses.CompletedRequestsAdapter;
import com.g7tech.guribile.HelperClasses.RateRequestsAdapter;
import com.g7tech.guribile.HelperClasses.SessionManager;
import com.g7tech.guribile.Model.ApprovedRequests;
import com.g7tech.guribile.Model.CompletedRequests;
import com.g7tech.guribile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RatingActivity extends AppCompatActivity {

    ProgressBar progressBar;
    List<ApprovedRequests> RateRequestsList;
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
        setContentView(R.layout.activity_rating);

        emptyRequest = findViewById(R.id.emptyRequest);

        URLL = AppConfig.URL_GET_RATING_REQUESTS+session.getId();
        Log.e("urlll",URLL);

        progressBar = findViewById(R.id.progress_circular);
        RateRequestsList = new ArrayList<>();
        loadCompletedRequests();
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    private void loadCompletedRequests() {
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

                                    JSONObject rateRequestsList = array.getJSONObject(i);

                                    RateRequestsList.add(new ApprovedRequests(
                                            rateRequestsList.getInt("id"),
                                            rateRequestsList.getString("title"),
                                            rateRequestsList.getString("message"),
                                            rateRequestsList.getString("status"),
                                            rateRequestsList.getString("created_at")
                                    ));
                                }
                                RateRequestsAdapter adapter = new RateRequestsAdapter(RatingActivity.this, RateRequestsList);
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
