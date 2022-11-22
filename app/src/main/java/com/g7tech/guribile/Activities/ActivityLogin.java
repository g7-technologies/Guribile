package com.g7tech.guribile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.g7tech.guribile.HelperClasses.SessionManager;
import com.g7tech.guribile.R;

import java.util.Locale;

public class ActivityLogin extends AppCompatActivity {

    EditText number;
    Button register_btn;
    String Number;
    SessionManager session;

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
        setContentView(R.layout.activity_login);

        number = findViewById(R.id.number);
        register_btn = findViewById(R.id.register_btn);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number.getText().length() == 0){
                    if (session.getLanguage().length() == 6){
                        number.setError("Kudar Lambarkaaga ..!");
                    }
                    else {
                        number.setError("Add your Number..!");
                    }
                    number.requestFocus();
                }else{
                    Number = "+92"+number.getText().toString().replace(" ","");
                    Intent intent = new Intent(getApplicationContext(),VerificationCodeActivity.class);
                    intent.putExtra("Number",Number);
                    startActivity(intent);
                    finish();
                }
            }
        });


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
