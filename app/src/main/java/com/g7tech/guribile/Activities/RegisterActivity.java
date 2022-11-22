package com.g7tech.guribile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText name,email,number;
    Button register_btn;
    SessionManager session;
    private static final String TAG = RegisterActivity.class.getSimpleName();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


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
        setContentView(R.layout.activity_register);


        name = findViewById(R.id.edt_name);
        email = findViewById(R.id.edt_email);
        number = findViewById(R.id.edt_number);
        register_btn = findViewById(R.id.register_button);

        number.setText(session.getPhone());

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().length() == 0){
                    if (session.getLanguage().length() == 6){
                        name.setError("Goobtu Waa Loo Baahan Yahay");
                    }
                    else {
                        name.setError("Field Required");
                    }
                    name.requestFocus();
                }
                else if(email.getText().length() == 0){
                    if (session.getLanguage().length() == 6){
                        email.setError("Goobtu Waa Loo Baahan Yahay");
                    }
                    else {
                        email.setError("Field Required");
                    }
                    email.requestFocus();
                }
                else if(!email.getText().toString().trim().matches(emailPattern)){
                    if (session.getLanguage().length() == 6){
                        email.setError("Emailka sax maahan");
                    }
                    else {
                        email.setError("Email not correct");
                    }
                    email.requestFocus();
                }
                else if(number.getText().length() == 0){
                    if (session.getLanguage().length() == 6){
                        number.setError("Goobtu Waa Loo Baahan Yahay");
                    }
                    else {
                        number.setError("Field Required");
                    }
                    number.requestFocus();
                }
                else {
                    register(name.getText().toString(),email.getText().toString(),session.getPhone());
                }
            }
        });
    }

    private void register(final String name, final String email, final String phone) {

        String tag_string_req = "Accept Request";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Appointment Response: " + response);

                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        session.setLogin(true);
                        session.setId(jObj.getString("id"));
                        session.setName(jObj.getString("name"));
                        session.setEmail(jObj.getString("email"));
                        session.setPhone(jObj.getString("phone"));
                        if (session.getLanguage().length() == 6){
                            Toast.makeText(getApplicationContext(), "Diiwaan galinta si guul leh ..!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Registered Succesfully..!", Toast.LENGTH_LONG).show();
                        }
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        finish();
                    }
                    else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Appointment Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("name", String.valueOf(name));
                params.put("email", String.valueOf(email));
                params.put("phone", String.valueOf(phone));
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

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
