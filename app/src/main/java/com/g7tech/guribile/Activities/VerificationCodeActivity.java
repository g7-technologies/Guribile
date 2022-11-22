package com.g7tech.guribile.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.g7tech.guribile.HelperClasses.AppConfig;
import com.g7tech.guribile.HelperClasses.AppController;
import com.g7tech.guribile.HelperClasses.SessionManager;
import com.g7tech.guribile.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerificationCodeActivity extends AppCompatActivity {

    EditText code;
    Button sign_in;
    TextView resend;
    String Number,Id,Code;
    private SessionManager session;
    private ProgressDialog pDialog;
    boolean register = false;
    FirebaseAuth mAuth;
    private static final String TAG = VerificationCodeActivity.class.getSimpleName();

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
        setContentView(R.layout.activity_verification_code);

        code = findViewById(R.id.code);
        sign_in = findViewById(R.id.sign_in);
        resend = findViewById(R.id.resend);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        mAuth = FirebaseAuth.getInstance();

        Number = getIntent().getStringExtra("Number");

        checkLogin(Number);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (code.getText().toString().replace(" ","").length() == 0){
                    if (session.getLanguage().length() == 6){
                        code.setError("Gali Nambarka ..!");
                    }
                    else {
                        code.setError("Enter Code..!");
                    }
                    code.requestFocus();
                }else if(code.getText().toString().replace(" ","").length() != 6){
                    if (session.getLanguage().length() == 6){
                        code.setError("Gali Nambar sax ah ..!");
                    }
                    else {
                        code.setError("Enter Correct Code..!");
                    }
                    code.requestFocus();
                }else{
                    if (session.getLanguage().length() == 6){
                        pDialog.setMessage("Xaqiijinta ...");
                    }
                    else {
                        pDialog.setMessage("Authenticating...");
                    }
                    showDialog();
                    Code = code.getText().toString().replace(" ","");
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(Id, Code);
                    signInWithPhoneAuthCredential(credential);
                    transfer();
                }
            }
        });


        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin(Number);
            }
        });



    }

    private void checkLogin(final String number) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        if (session.getLanguage().length() == 6){
            pDialog.setMessage("Helitaanka Farriinta...");
        }
        else {
            pDialog.setMessage("Getting Message...");
        }
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    Log.e("response",response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        if(jObj.getInt("registered") == 1){
                            session.setLogin(true);
                            session.setId(jObj.getString("id"));
                            session.setName(jObj.getString("name"));
                            session.setEmail(jObj.getString("email"));
                            session.setPhone(jObj.getString("phone"));
                            register = true;
                            Log.e("step 1","phla number check");
                            SendVerificationCode();
                        }
                        else if(jObj.getInt("registered") == 0) {
                            //user not registered
                            session.setLogin(false);
                            session.setPhone(number);
                            register = false;
                            Log.e("step 1","phla number check register");
                            SendVerificationCode();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),jObj.getString("error_msg"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("number", number);

                return params;
            }

        };

        // Adding request to request queue
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

    private void SendVerificationCode() {
        Log.e("step 2","firebase");

        new CountDownTimer(60000,1000){

            @Override
            public void onTick(long l) {
                resend.setText(""+l/1000);
                resend.setEnabled(false);
            }

            @Override
            public void onFinish() {
                resend.setText("Resend");
                resend.setEnabled(true);
            }
        }.start();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                Number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(id, forceResendingToken);
                        Id = id;
                        hideDialog();
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        hideDialog();
                        if (session.getLanguage().length() == 6){
                            Toast.makeText(getApplicationContext(),"Fashilay..!",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Failed..!",Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            hideDialog();
                            transfer();
                            FirebaseUser user = task.getResult().getUser();

                        } else {
                            hideDialog();
                            if (session.getLanguage().length() == 6){
                                Toast.makeText(getApplicationContext(),"Xaqiijinta way Fashilantay ..!",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Verification Failed..!",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void transfer(){
        if(register){
            Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(intent);
            finish();
        }
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
