package com.example.trw.sampleroompersistence;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ViewPager viewPagerMenu;
    private TabLayout tabLayoutMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI();
    }

    private void initializeUI() {
        MenuAdapter adapter = new MenuAdapter(getSupportFragmentManager(), this);
        viewPagerMenu = findViewById(R.id.view_pager_menu);
        viewPagerMenu.setAdapter(adapter);

        tabLayoutMenu = findViewById(R.id.tab_layout_menu);
        tabLayoutMenu.setupWithViewPager(viewPagerMenu);
    }

}
