package com.example.Kirokhada.findmate.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.Kirokhada.R;
import com.example.Kirokhada.findmate.fragment.ListFragment;
import com.example.Kirokhada.findmate.fragment.MapFragment;
import com.example.Kirokhada.findmate.fragment.ProfileFragment;
import com.example.Kirokhada.findmate.fragment.ReviewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FindMateActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;  //bottom navigation view
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ListFragment listFragment;
    private MapFragment mapFragment;
    private ProfileFragment profileFragment;
    private ReviewFragment reviewFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findmate);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_list :
                        setFrag(0);
                        break;
                    case R.id.action_map :
                        setFrag(1);
                        break;
                    case R.id.action_profile :
                        setFrag(2);
                        break;
                    case R.id.action_review :
                        setFrag(3);
                        break;
                }

                return true;
            }
        });
        listFragment = new ListFragment();
        mapFragment = new MapFragment();
        profileFragment = new ProfileFragment();
        reviewFragment = new ReviewFragment();
        setFrag(0);     //??? fragment ????????? ???????????? ????????? ??? ?????????
    }


    //fragment ????????? ???????????? ?????????
    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();     //???????????? fragment ??????

        switch (n){
            case 0:
                ft.replace(R.id.frame_content, listFragment);
                ft.commit();        //????????? ???????????????.
                break;
            case 1:
                ft.replace(R.id.frame_content, mapFragment);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.frame_content, profileFragment);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.frame_content, reviewFragment);
                ft.commit();
                break;
        }
    }
}
