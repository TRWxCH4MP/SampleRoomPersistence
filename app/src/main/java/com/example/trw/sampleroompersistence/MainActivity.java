package com.example.trw.sampleroompersistence;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener {

    String TAG = "MainActivity";
    ViewPager viewPagerMenu;
    TextView textViewHome;
    TextView textViewAward;
    TextView textViewManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUI();
    }

    private void initializeUI() {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPagerMenu = (ViewPager) findViewById(R.id.pager_menu);
        viewPagerMenu.setAdapter(adapter);

        textViewHome = (TextView) findViewById(R.id.tv_home);
        textViewAward = (TextView) findViewById(R.id.tv_award);
        textViewManage = (TextView) findViewById(R.id.tv_manage);

        textViewHome.setOnClickListener(this);
        textViewAward.setOnClickListener(this);
        textViewManage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_home:
                if (viewPagerMenu.getCurrentItem() == 1) {
                    viewPagerMenu.setCurrentItem(viewPagerMenu.getCurrentItem() - 1);
                } else if (viewPagerMenu.getCurrentItem() == 2) {
                    viewPagerMenu.setCurrentItem(viewPagerMenu.getCurrentItem() - 2);
                }
                break;
            case R.id.tv_award:
                if (viewPagerMenu.getCurrentItem() == 0) {
                    viewPagerMenu.setCurrentItem(viewPagerMenu.getCurrentItem() + 1);
                } else if (viewPagerMenu.getCurrentItem() == 2) {
                    viewPagerMenu.setCurrentItem(viewPagerMenu.getCurrentItem() - 1);
                }
                break;
            case R.id.tv_manage:
                if (viewPagerMenu.getCurrentItem() == 0) {
                    viewPagerMenu.setCurrentItem(viewPagerMenu.getCurrentItem() + 1);
                    viewPagerMenu.setCurrentItem(viewPagerMenu.getCurrentItem() + 2);
                } else if (viewPagerMenu.getCurrentItem() == 1) {
                    viewPagerMenu.setCurrentItem(viewPagerMenu.getCurrentItem() + 1);
                }
                break;
        }
    }

}
