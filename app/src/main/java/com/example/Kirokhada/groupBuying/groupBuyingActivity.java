package com.example.Kirokhada.groupBuying;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.Kirokhada.R;
import com.example.Kirokhada.findmate.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class groupBuyingActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;  //bottom navigation view
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ProfileFragment profileFragment;
    private groupBuyingFragment groupBuyingFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupbuying);

        bottomNavigationView = findViewById(R.id.bottom_nav_2);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_list_ :
                        setFrag(0);
                        break;
                    case R.id.action_profile :
                        setFrag(1);
                        break;
                }

                return true;
            }
        });
        groupBuyingFragment =  new groupBuyingFragment();
        profileFragment = new ProfileFragment();
        setFrag(0);     //첫 fragment 화면을 무엇으로 지정할 지 설정함
    }


    //fragment 교체가 일어나는 실행문
    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();     //실제적인 fragment 교체

        switch (n){
            case 0:
                ft.replace(R.id.frame_content, groupBuyingFragment);
                ft.commit();        //저장을 의미합니다.
                break;
            case 1:
                ft.replace(R.id.frame_content, profileFragment);
                ft.commit();
                break;

        }
    }
}
