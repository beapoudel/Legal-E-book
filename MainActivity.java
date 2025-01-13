package com.example.legallib;

import static android.R.layout.simple_list_item_1;

import android.net.http.NetworkException;
import android.os.Bundle;
import android.util.AndroidException;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout main;
    NavigationView navigation;
    Toolbar toolbar;
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        main = findViewById(R.id.main);
        navigation = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, main, toolbar, R.string.opendrawer, R.string.closedrawer);
        main.addDrawerListener(toggle);
        toggle.syncState();
        homeFragment(new HomeFragment());
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int ID = item.getItemId();
                if(ID == R.id.optHome){
                    homeFragment(new HomeFragment());
                } else if (ID == R.id.optYourLib) {
                    Toast.makeText(MainActivity.this,"you are in lib", Toast.LENGTH_LONG).show();

                } else if (ID == R.id.optsetting) {
                    Toast.makeText(MainActivity.this, "you are in setting", Toast.LENGTH_LONG).show();
                };
                main.closeDrawer(GravityCompat.START);
                return true;
            }

        });
    }

    @Override
    public void onBackPressed() {
        if (main.isDrawerOpen(GravityCompat.START)){
            main.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
        }

    private void homeFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.contain1, fragment);
        ft.commit();

    }
}