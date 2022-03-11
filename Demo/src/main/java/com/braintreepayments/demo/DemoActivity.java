package com.braintreepayments.demo;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class DemoActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, ActionBar.OnNavigationListener {

    private AppBarConfiguration appBarConfiguration;
    private ActionBarController actionBarController = new ActionBarController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        setupActionBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actionBarController.updateTitle(this);

        if (BuildConfig.DEBUG && ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private NavController getNavController() {
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        return NavHostFragment.findNavController(navHostFragment);
    }

    private void setupActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavController navController = getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = getNavController();
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.change_authorization || itemId == R.id.change_environment) {
            NavController navController = getNavController();
            navController.navigate(R.id.action_goto_change_auth_environment);
            return false;
        }

        if (itemId == R.id.request_settings) {
            NavController navController = getNavController();
            navController.navigate(R.id.open_settings_fragment);
            return true;
        }
        return false;
    }

    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        String env = getResources().getStringArray(R.array.environments)[itemPosition];
        if (!Settings.getEnvironment(this).equals(env)) {
            Settings.setEnvironment(this, env);
        }
        return true;
    }
}