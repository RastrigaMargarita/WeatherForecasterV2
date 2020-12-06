package com.margretcraft.weatherforecasterv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.margretcraft.weatherforecasterv2.model.TownClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainNdActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TownClass currentTown;
    private boolean windmes;
    private boolean tempmes;
    private String[] days;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavController navController;
    private SharedPreferences sharedPref;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        if (sharedPref.getBoolean("theme", true)) {
            setTheme(R.style.AppThemeViolet);
        } else {
            setTheme(R.style.AppThemeGreen);
        }
        setContentView(R.layout.activity_main_nd);

        if (savedInstanceState == null) {
            currentTown = new TownClass(sharedPref.getString("townName", getString(R.string.Moscow)),
                    sharedPref.getString("townPoint", getString(R.string.MoscowPoint)),
                    sharedPref.getString("townZone", "UTF+3"));
            windmes = sharedPref.getBoolean("windmes", true);
            tempmes = sharedPref.getBoolean("tempmes", true);

        } else {
            currentTown = savedInstanceState.getParcelable("Town");
            windmes = savedInstanceState.getBoolean("wind");
            tempmes = savedInstanceState.getBoolean("temp");
        }

        Date showDay = new Date();
        SimpleDateFormat sdfOneDay = new SimpleDateFormat("dd.MM", Locale.getDefault());

        days = new String[7];

        for (int i = 0; i < 7; i++) {
            days[i] = "" + sdfOneDay.format(new Date(showDay.getTime() + (i + 1) * 24 * 60 * 60 * 1000));

        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (sharedPref.getBoolean("theme", true)) {
            navigationView.setBackgroundColor(getColor(R.color.blue));

        } else {
            navigationView.setBackgroundColor(getColor(R.color.colorPrimaryGreen));
        }

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_weather, R.id.nav_forecast, R.id.nav_town, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_nd, menu);

        menu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{getString(R.string.myEmail)});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.SendEMail));

                startActivity(Intent.createChooser(emailIntent, getString(R.string.SendMail)));
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    protected void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putParcelable("Town", currentTown);
        saveInstanceState.putBoolean("wind", windmes);
        saveInstanceState.putBoolean("temp", tempmes);
    }

    public TownClass getCurrentTown() {
        return currentTown;
    }

    public boolean isWindmes() {
        return windmes;
    }

    public boolean isTempmes() {
        return tempmes;
    }

    public void setCurrentTown(TownClass currentTown) {
        this.currentTown = currentTown;
    }

    public String[] getDays() {
        return days;
    }

    public SharedPreferences getSharedPref() {
        return sharedPref;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        navController.navigate(id);
        if (id == R.id.nav_weather) {
            toolbar.getMenu().getItem(0).setVisible(false);
            toolbar.setBackground(null);
        } else if (id == R.id.nav_town) {
            toolbar.getMenu().getItem(0).setVisible(true);
            toolbar.setBackgroundColor(getColor(R.color.white));
            toolbar.setBackground(getDrawable(R.drawable.sky));
        } else {
            toolbar.getMenu().getItem(0).setVisible(false);
            toolbar.setBackgroundColor(getColor(R.color.white));
            toolbar.setBackground(getDrawable(R.drawable.sky));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onTownChoose(TownClass townClass) {
        setCurrentTown(townClass);
        toolbar.setBackground(null);
        toolbar.getMenu().getItem(0).setVisible(false);
        navController.navigate(R.id.nav_weather);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("townName", currentTown.getName());
        editor.putString("townPoint", currentTown.getPoint());
        editor.putString("townZone", currentTown.getTimeZone());
        editor.apply();
    }

    public void setSetting(String key, boolean b) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, b);
        editor.apply();
        if (key.equals("windmes")) {
            windmes = b;
        } else if (key.equals("tempmes")) {
            tempmes = b;
        }
        ;
    }
}