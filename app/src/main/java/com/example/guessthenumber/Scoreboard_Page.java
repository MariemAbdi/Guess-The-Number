package com.example.guessthenumber;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class Scoreboard_Page extends AppCompatActivity implements TabLayoutMediator.TabConfigurationStrategy{

    TabLayout tabLayout;
    ViewPager2 viewPager;
    ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard_page);

        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager);


        titles = new ArrayList<String>();
        titles.add(getString(R.string.easy));
        titles.add(getString(R.string.medium));
        titles.add(getString(R.string.hard));

        setViewPagerAdapter();
        new TabLayoutMediator(tabLayout, viewPager, this).attach();

    }

    public void setViewPagerAdapter() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        ArrayList<Fragment> fragmentList = new ArrayList<>(); //creates an ArrayList of Fragments
        fragmentList.add(new EasyFragment());
        fragmentList.add(new MediumFragment());
        fragmentList.add(new HardFragment());
        viewPagerAdapter.setData(fragmentList); //sets the data for the adapter
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        tab.setText(titles.get(position));
    }

    //go back to the previous page
    public void go_back_game(View view){
        Scoreboard_Page.this.finish();
    }
}


