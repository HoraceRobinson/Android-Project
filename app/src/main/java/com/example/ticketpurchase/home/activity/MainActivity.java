package com.example.ticketpurchase.home.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ticketpurchase.home.fragment.FunctionFragment;
import com.example.ticketpurchase.home.fragment.GameFragment;
import com.example.ticketpurchase.home.fragment.HomeFragment;
import com.example.ticketpurchase.R;
import com.example.ticketpurchase.home.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation);
//        startActivity(new Intent(this, HomeActivity.class));

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new HomeFragment()).commit();
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.user:
                        fragment = new UserFragment();
                        break;
                    case R.id.function:
                        fragment = new FunctionFragment();
                        break;
                    case R.id.game:
                        fragment = new GameFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment).commit();
                return true;
            }
        });
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        //????????????
//        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
//    }
}