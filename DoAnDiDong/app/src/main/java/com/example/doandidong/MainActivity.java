package com.example.doandidong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnv=findViewById(R.id.bottom_menu);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                display(item.getItemId());

                return true;
            }
        });
        display(R.id.mnuHome);
    }

    void display(int id){
        Fragment fragment = null;

        switch (id){
            case R.id.mnuHome:
                fragment = new HomeFragment();
                getSupportActionBar().setTitle("Trang Chủ");
                break;
            case R.id.mnuAppointment:
                fragment = new AppointmentFragment();
                getSupportActionBar().setTitle("Lịch Khám");
                break;
            case R.id.mnuAuth:
                fragment=new AuthFragment();
                getSupportActionBar().setTitle("Tài Khoản");
                break;
            case R.id.mnuSettings:
                fragment = new SettingFragment();
                getSupportActionBar().setTitle("Cài Đặt");
                break;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }
}