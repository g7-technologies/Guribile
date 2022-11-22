package com.g7tech.guribile.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.g7tech.guribile.HelperClasses.SessionManager;
import com.g7tech.guribile.R;

import java.util.Locale;

public class Language extends AppCompatActivity {

    TextView eng,somali;
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
        setContentView(R.layout.activity_language);

        eng = findViewById(R.id.eng);
        somali = findViewById(R.id.som);

        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setLanguage("English");
                finish();
            }
        });

        somali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setLanguage("Somali");
                finish();
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
