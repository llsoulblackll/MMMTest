package com.mmm.mmmtest.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.mmm.mmmtest.R;
import com.mmm.mmmtest.ui.fragment.ListFragment;
import com.mmm.mmmtest.ui.fragment.LocationFragment;
import com.mmm.mmmtest.ui.fragment.TabsFragment;

public class MainActivity extends AppCompatActivity {

    FragmentManager fm;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_list:
                    navigate(new TabsFragment());
                    return true;
                case R.id.navigation_notifications:
                    navigate(new Fragment());
                    return true;
                case R.id.navigation_location:
                    navigate(new LocationFragment());
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = this.getSupportFragmentManager();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigate(new TabsFragment());
    }

    void navigate(Fragment fragment){
        fm.beginTransaction().replace(R.id.content, fragment).commit();
    }



}
