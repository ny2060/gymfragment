package com.google.firebase.quickstart.database.java.fragment;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.quickstart.database.R;

public class MainPage extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;  // 바텀 네비게이션 뷰 (하단바)
    private FragmentManager fm;
    private FragmentTransaction ft;
    private MapFragment mapFragment;
    private SearchFragment searchFragment;
    private CommuFragment commuFragment;
    private MyFragment myFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.id.ac);
        setContentView(R.layout.activity_chatt);

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_map:
                        setFrag(0);
                        break;
                    case R.id.action_search:
                        setFrag(1);
                        break;
                    case R.id.action_community:
                        setFrag(2);
                        break;
                    case R.id.action_mypage:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });
        mapFragment = new MapFragment();
        searchFragment = new SearchFragment();
        commuFragment = new CommuFragment();
        myFragment = new MyFragment();
        setFrag(0); // 첫 프래그먼트 화면을 무엇으로 지정해줄 것인지 선택
    }

    // 프래그먼트 교체가 일어나는 실행문이다.
    private void setFrag(int n){

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction(); //실질적인 fragment 교체를 위해 사용
        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, mapFragment);
                ft.commit(); //저장
                break;
            case 1:
                ft.replace(R.id.main_frame, searchFragment);
                ft.commit(); //저장
                break;
            case 2:
                ft.replace(R.id.main_frame, commuFragment);
                ft.commit(); //저장
                break;
            case 3:
                ft.replace(R.id.main_frame, myFragment);
                ft.commit(); //저장
                break;
        }
    }
}
