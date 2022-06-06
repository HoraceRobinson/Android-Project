package com.example.ticketpurchase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation);
//        startActivity(new Intent(this, HomeActivity.class));

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new HomeFragement()).commit();
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                        fragment = new HomeFragement();
                        break;
                    case R.id.user:
                        fragment = new UserFragement();
                        break;
                    case R.id.game:
                        fragment = new GameFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment).commit();
                return true;
            }
        });
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        //注入字体
//        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
//    }
}