package com.wordpress.necessitateapps.politik;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.wordpress.necessitateapps.politik.Fragments.PetitionsFragment;
import com.wordpress.necessitateapps.politik.Fragments.ProfileFragment;
import com.wordpress.necessitateapps.politik.Fragments.TrendingFragment;
import com.wordpress.necessitateapps.politik.Services.ArticleAdapter;


public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment fragment ;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_trending:
                    fragment= new TrendingFragment();
                    break;

                case R.id.nav_petitions:
                    fragment= new PetitionsFragment();
                    break;

                case R.id.nav_profile:
                    fragment= new ProfileFragment();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Impossible Bug", Toast.LENGTH_LONG).show();

            }


            fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("null").commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ArticleAdapter mAdapter;
//        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//            if(extras == null) {
//                mAdapter= null;
//            } else {
//                mAdapter= (ArticleAdapter)extras.getSerializable("adapter");
//            }
//        } else {
//            mAdapter= (ArticleAdapter)savedInstanceState.getSerializable("adapter");
//        }


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager.beginTransaction().add(R.id.container, new TrendingFragment()).commit();
    }

}
