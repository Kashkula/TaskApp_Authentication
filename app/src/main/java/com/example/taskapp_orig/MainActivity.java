package com.example.taskapp_orig;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_profile)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        boolean isShown = new Prefs(this).isShown();
        if (!isShown)
            navController.navigate(R.id.boardFragment);

        navController.navigate(R.id.action_navigation_home_to_registerFragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener(
                new NavController.OnDestinationChangedListener() {
                    @Override
                    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                        if (destination.getId() == R.id.navigation_form || destination.getId() == R.id.boardFragment
                                || destination.getId() == R.id.navigation_profile || destination.getId() == R.id.registerFragment)
                            navView.setVisibility(View.GONE);
                        else
                            navView.setVisibility(View.VISIBLE);
                        if (destination.getId() == R.id.navigation_dashboard || destination.getId() == R.id.navigation_notifications
                                || destination.getId() == R.id.navigation_home || destination.getId() == R.id.navigation_form)
                            getSupportActionBar().show();
                        else
                            getSupportActionBar().hide();

                    }
                }
        );
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}