package com.example.moacall;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.moacall.topElement.acceptFrag;
import com.example.moacall.topElement.chattingFrag;
import com.example.moacall.topElement.completeFrag;
import com.example.moacall.topElement.dispatchFrag;
import com.example.moacall.topElement.settingFrag;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    public Bundle mBundle;
    TabLayout tabs;
    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;
    Fragment fragment4;
    Fragment fragment5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("접수"));
        tabs.addTab(tabs.newTab().setText("배차"));
        tabs.addTab(tabs.newTab().setText("완료"));
        tabs.addTab(tabs.newTab().setText("채팅"));
        tabs.addTab(tabs.newTab().setText("설정"));

        fragment1 = new acceptFrag();
        fragment2 = new dispatchFrag();
        fragment3 = new completeFrag();
        fragment4 = new chattingFrag();
        fragment5 = new settingFrag();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "Seleted Tab : " + position);

                Fragment seleted = null;

                if (position == 0) {
                    seleted = fragment1;
                }
                else if (position == 1) {
                    seleted = fragment2;
                }
                else if (position == 2) {
                    seleted = fragment3;
                }
                else if (position == 3) {
                    seleted = fragment4;
                }
                else if (position == 4) {
                    seleted = fragment5;
                }


                getSupportFragmentManager().beginTransaction().replace(R.id.container, seleted).commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    public void fragBtnClick(Bundle bundle) {
        this.mBundle = bundle;
    }



}
