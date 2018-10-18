package com.wordpress.necessitateapps.politik.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ramotion.foldingcell.FoldingCell;
import com.wordpress.necessitateapps.politik.ARActivity;
import com.wordpress.necessitateapps.politik.Services.API;
import com.wordpress.necessitateapps.politik.Services.ArticleAdapter;
import com.wordpress.necessitateapps.politik.Services.ArticleList;
import com.wordpress.necessitateapps.politik.R;
import com.wordpress.necessitateapps.politik.SplashActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    //private List<ArticleGetter> savedList = new ArrayList<>();
    ListView listView;
    ArticleAdapter mAdapter = null;
    private boolean isCalled = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        // get list view
        ImageView image = view.findViewById(R.id.image_ar);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ARActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        isCalled = false;
    }

}
