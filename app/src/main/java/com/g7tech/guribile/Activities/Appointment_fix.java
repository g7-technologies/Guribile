package com.g7tech.guribile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.g7tech.guribile.HelperClasses.AppConfig;
import com.g7tech.guribile.HelperClasses.AppController;
import com.g7tech.guribile.HelperClasses.SessionManager;
import com.g7tech.guribile.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Appointment_fix extends AppCompatActivity {

    private static final String TAG = Appointment_fix.class.getSimpleName();
    String worker_id,date_fixed,description_fixed,time_fixed,client_id,service_id;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ProgressDialog pDialog;
    EditText date_edt,description;
    TimePicker timePicker;
    TextView error_time;
    Button submit;
    SessionManager session;
    private Calendar calendar;
    private String format = "";



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
        setContentView(R.layout.activity_appointment_fix);

        session = new SessionManager(getApplicationContext());
        worker_id = session.getWorker();
        client_id = session.getId();
        service_id = session.getService();
        calendar = Calendar.getInstance();

        date_edt = findViewById(R.id.Date);
        date_edt.requestFocus();
        description = findViewById(R.id.Description);
        timePicker = findViewById(R.id.timePicker);
        submit = findViewById(R.id.submit);
        error_time = findViewById(R.id.error_time);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);


        date_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Appointment_fix.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: yyy-mm-dd: " + year + "-" + month + "-" + day);

                String date = year + "-" + month + "-" + day;
                date_edt.setText(date);
            }
        };


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                date_fixed = date_edt.getText().toString().trim();
                description_fixed = description.getText().toString();

                if(date_fixed.isEmpty()){
                    date_edt.requestFocus();
                    if (session.getLanguage().length() == 6){
                        date_edt.setError("Xulo Taariiqda Ballamada Fadlan ...!");
                    }
                    else {
                        date_edt.setError("Select Appointment Date Please...!");
                    }

                }
                else if(time_fixed.isEmpty()){
                    error_time.requestFocus();
                    error_time.setVisibility(View.VISIBLE);
                }
                else if (worker_id.isEmpty()){
                    if (session.getLanguage().length() == 6){
                        Toast.makeText(getApplicationContext(),"Fadlan Dooro Shaqada Marka hore ...!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Please Select Worker First...!",Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    setTime(v);
                    bookAppointment(client_id,worker_id,date_fixed,time_fixed,description_fixed,service_id);
                }
            }
        });



    }

    private void bookAppointment(final String client_id,final String worker_id,final String date_fixed,final String time_fixed,final String description_fixed,final String service_id){
        // Tag used to cancel the request
        String tag_string_req = "fix_appointment";
        if (session.getLanguage().length() == 6){
            pDialog.setMessage("Samaynta Ballanka ...");
        }
        else {
            pDialog.setMessage("Fixing Appointment...");
        }

        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_FIX_APPOINTMENT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Appointment Response: " + response);
                hideDialog();

                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        if (session.getLanguage().length() == 6){
                            Toast.makeText(getApplicationContext(), "Ballanta Ballanta si ku-meel-gaar ah ..!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Appointment Fixed Succesfully..!", Toast.LENGTH_LONG).show();
                        }
                        Intent intent001 = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(intent001);
                        finish();
                    }
                    else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Appointment Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("client_id", client_id);
                params.put("worker_id", worker_id);
                params.put("date_fixed", date_fixed);
                params.put("time_fixed", time_fixed);
                params.put("description_fixed", description_fixed);
                params.put("service_id",service_id);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void setTime(View view) {
        int hour = timePicker.getCurrentHour();
        int min = timePicker.getCurrentMinute();
        showTime(hour, min);
    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        time_fixed = hour+" : "+min+" "+format;
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
