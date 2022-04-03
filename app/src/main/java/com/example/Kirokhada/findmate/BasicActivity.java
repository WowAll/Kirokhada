package com.example.Kirokhada.findmate;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Kirokhada.R;

public class BasicActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

   // @Override
   // public void setContentView(@LayoutRes int layoutResID) {
       // super.setContentView(layoutResID);

       // Toolbar myToolbar = findViewById(R.id.toolbar);
       /// setSupportActionBar(myToolbar);
   // }

    public void setToolbarTitle(String title){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title);
        }
    }
}