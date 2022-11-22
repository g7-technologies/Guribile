package com.g7tech.guribile.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import com.g7tech.guribile.HelperClasses.SessionManager;
import com.g7tech.guribile.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;


public class DashboardActivity extends AppCompatActivity {

    LinearLayout card1, card2, card3, card4, card5, card6, card7, card8, card9, card10, card11,
            card12, card13, card14;
    SessionManager session;
    DrawerLayout dl;
    private ActionBarDrawerToggle t;
    NavigationView nv;


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
        setContentView(R.layout.activity_dashboard);

        dl = findViewById(R.id.drawer);
        t = new ActionBarDrawerToggle(this, dl, R.string.open, R.string.close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = findViewById(R.id.navigation);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.completed:
                        Intent intent = new Intent(getApplicationContext(),Completed_Requests.class);
                        startActivity(intent);
                        break;
                    case R.id.accepted:
                        Intent intent1 = new Intent(getApplicationContext(),Approved_Requests.class);
                        startActivity(intent1);
                        break;
                    case R.id.declined:
                        Intent intent2 = new Intent(getApplicationContext(),Declined_Requests.class);
                        startActivity(intent2);
                        break;
                    case R.id.pending:
                        Intent intent3 = new Intent(getApplicationContext(),Pending_Requests.class);
                        startActivity(intent3);
                        break;
                    case R.id.rating:
                        Intent intent4 = new Intent(getApplicationContext(),RatingActivity.class);
                        startActivity(intent4);
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });


        card1 = findViewById(R.id.card_1);
        card2 = findViewById(R.id.card_2);
        card3 = findViewById(R.id.card_3);
        card4 = findViewById(R.id.card_4);
        card5 = findViewById(R.id.card_5);
        card6 = findViewById(R.id.card_6);
        card7 = findViewById(R.id.card_7);
        card8 = findViewById(R.id.card_8);
        card9 = findViewById(R.id.card_9);
        card10 = findViewById(R.id.card_10);
        card11 = findViewById(R.id.card_11);
        card12 = findViewById(R.id.card_12);
        card13 = findViewById(R.id.card_13);
        card14 = findViewById(R.id.card_14);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PendingRequestActivity.class);
                startActivity(intent);
                session.setService("1");
                Log.e("Service", session.getService());

            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PendingRequestActivity.class);
                startActivity(intent);
                session.setService("2");
                Log.e("Service", session.getService());

            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PendingRequestActivity.class);
                startActivity(intent);
                session.setService("3");
                Log.e("Service", session.getService());


            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PendingRequestActivity.class);
                startActivity(intent);
                session.setService("4");
                Log.e("Service", session.getService());


            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PendingRequestActivity.class);
                startActivity(intent);
                session.setService("5");
                Log.e("Service", session.getService());

            }
        });
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PendingRequestActivity.class);
                startActivity(intent);
                session.setService("6");
                Log.e("Service", session.getService());

            }
        });
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PendingRequestActivity.class);
                startActivity(intent);
                session.setService("7");
                Log.e("Service", session.getService());

            }
        });
        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PendingRequestActivity.class);
                startActivity(intent);
                session.setService("8");
                Log.e("Service", session.getService());

            }
        });

        card9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PendingRequestActivity.class);
                startActivity(intent);
                session.setService("9");
                Log.e("Service", session.getService());

            }
        });
        card10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PendingRequestActivity.class);
                startActivity(intent);
                session.setService("10");
                Log.e("Service", session.getService());

            }
        });
        card11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PendingRequestActivity.class);
                startActivity(intent);
                session.setService("11");
                Log.e("Service", session.getService());

            }
        });
        card12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PendingRequestActivity.class);
                startActivity(intent);
                session.setService("12");
                Log.e("Service", session.getService());

            }
        });
        card13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PendingRequestActivity.class);
                startActivity(intent);
                session.setService("13");
                Log.e("Service", session.getService());

            }
        });
        card14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PendingRequestActivity.class);
                startActivity(intent);
                session.setService("14");
                Log.e("Service", session.getService());

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        if (t.onOptionsItemSelected(item))
            return true;
        switch (item.getItemId()) {
            case R.id.chng:
                Intent intent = new Intent(getApplicationContext(),Language.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                session.setLogin(false);
                session.setId("");
                session.setEmail("");
                session.setName("");
                session.setPhone("");
                session.setService("");
                session.setWorker("");
                session.setLat("");
                session.setLongt("");
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),ActivityLogin.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
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