package org.chunghyun.plannerforplanman;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.chunghyun.plannerforplanman.Add_Plan.Fragment_addPlanPage;
import org.chunghyun.plannerforplanman.DelayPlan.Fragment_delayplan;
import org.chunghyun.plannerforplanman.Home_Plan.Fragment_Home;

public class MainActivity extends AppCompatActivity {

    private Fragment_Home frag_home;
    private Fragment_addPlanPage frag_plan;
    private Fragment_delayplan frag_calendar;
    private AdView mAdview;

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
        frag_calendar = new Fragment_delayplan();

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
        
        // 애드몹 관련
        MobileAds.initialize(this, getString(R.string.admob_app_id));

        mAdview = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);

        // 광고가 제대로 로드 되는지 테스트 하기 위한 코드입니다.

        mAdview.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                // 광고가 문제 없이 로드시 출력됩니다.
                Log.d("@@@", "onAdLoaded");
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                // 광고 로드에 문제가 있을시 출력됩니다.
                Log.d("@@@", "onAdFailedToLoad " + errorCode);
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }
            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }
            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

        });

    }
}