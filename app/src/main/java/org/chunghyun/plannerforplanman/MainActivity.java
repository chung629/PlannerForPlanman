package org.chunghyun.plannerforplanman;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.chunghyun.plannerforplanman.Add_Plan.Fragment_addPlanPage;
import org.chunghyun.plannerforplanman.Calendar.Fragment_calendar;
import org.chunghyun.plannerforplanman.Home_Plan.Fragment_Home;

public class MainActivity extends AppCompatActivity {

    private Fragment_Home frag_home;
    private Fragment_addPlanPage frag_plan;
    private Fragment_calendar frag_calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 액션바 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //하단 네비게이션 바 설정 시작

        frag_home = new Fragment_Home();
        frag_plan = new Fragment_addPlanPage();
        frag_calendar = new Fragment_calendar();

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag_home).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag_home).commit();
                        return true;
                    case R.id.action_plan:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag_plan).commit();
                        return true;
                    case R.id.action_calendar:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag_calendar).commit();
                        return true;
                }
                return false;
            }
        });
        //하단 네비게이션 바 설정 끝
    }
}