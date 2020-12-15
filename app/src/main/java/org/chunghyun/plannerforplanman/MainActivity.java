package org.chunghyun.plannerforplanman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Fragment_Home frag_home;
    private Fragment_addPlanPage frag_plan;
    private Fragment_calendar frag_calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //하단 네비게이션 바 설정
        bottomNavigationView = findViewById(R.id.bottomNavBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.action_home:
                        setFrag(0);
                        break;
                    case R.id.action_plan:
                        setFrag(1);
                        break;
                    case R.id.action_calendar:
                        setFrag(2);
                        break;
                }
                return false;
            }
        });
        frag_home = new Fragment_Home();
        frag_plan = new Fragment_addPlanPage();
        frag_calendar = new Fragment_calendar();
        setFrag(0);
    }

    //프래그먼트 교체
    private void setFrag(int page)
    {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (page)
        {
            case 0:
                ft.replace(R.id.framelayout, frag_home);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.framelayout, frag_plan);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.framelayout, frag_calendar);
                ft.commit();
                break;
        }
    }
}