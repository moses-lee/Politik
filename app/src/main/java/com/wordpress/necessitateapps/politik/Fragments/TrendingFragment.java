package com.wordpress.necessitateapps.politik.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.ramotion.foldingcell.FoldingCell;
import com.wordpress.necessitateapps.politik.Services.API;
import com.wordpress.necessitateapps.politik.Services.ArticleAdapter;

import com.wordpress.necessitateapps.politik.Services.ArticleList;

import com.wordpress.necessitateapps.politik.R;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class TrendingFragment extends Fragment {
//
//    //private List<ArticleGetter> savedList = new ArrayList<>();
//    RecyclerView recyclerView;
//    ArticleAdapter mAdapter=null;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view=inflater.inflate(R.layout.fragment_trending, container, false);
//
//        recyclerView = view.findViewById(R.id.recycler);
//
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//        mLayoutManager.setStackFromEnd(true);
//        mLayoutManager.setReverseLayout(true);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setAdapter(mAdapter);
//
//
//        FloatingActionButton fab = view.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getContext(), ArticleActivity.class);
//                startActivity(i);
//
//            }
//        });
//
//        getData();
//        return view;
//    }
//
//    private void getData(){
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(API.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        API api = retrofit.create(API.class);
//
//        Call<ArticleList> call = api.getArticles();
//        call.enqueue(new Callback<ArticleList>() {
//            @Override
//            public void onResponse(@NonNull Call<ArticleList> call, @NonNull Response<ArticleList> response) {
//                ArticleList list = response.body();
//                recyclerView.setAdapter(new ArticleAdapter(getActivity(),list.getArticles()));
//
//            }
//
//            @Override
//            public void onFailure(Call<ArticleList> call, Throwable throwable) {
//                Log.d("ERR:", throwable.getMessage() );
//
//            }
//        });
//
//    }

//    private void loadSavedData() {
//
//        String name;
//        String image;
//
//        //open internal file
//        try {
//            //get hashmap as object
//            FileInputStream inputStream = getActivity().openFileInput("saved");
//            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
//            Map hashSaved = (LinkedHashMap) objectInputStream.readObject();
//            objectInputStream.close();
//
//            if(!hashSaved.isEmpty()){
//                textEmpty.setVisibility(View.GONE);
//            }
//
//            for (Object typeKey: hashSaved.keySet()) {
//                //key: rest name
//                //value: rest image
//                name = typeKey.toString();
//                image = hashSaved.get(name).toString();
//
//                //sends data to adapter
//                SavedGetter savedGetter = new SavedGetter(name, image);
//                savedList.add(savedGetter);
//            }
//
//        }catch (ClassNotFoundException | IOException e){
//            e.printStackTrace();
//        }
//
//        mAdapter.notifyDataSetChanged();
//    }
//}



public class TrendingFragment extends Fragment {

    //private List<ArticleGetter> savedList = new ArrayList<>();
    ListView listView;
    ArticleAdapter mAdapter = null;
    //to not repeat calls



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_trending, container, false);

        // get list view
        listView = view.findViewById(R.id.list_view);



        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        Call<ArticleList> call = api.getArticles();


        call.enqueue(new Callback<ArticleList>() {
            @Override
            public void onResponse(@NonNull Call<ArticleList> call, @NonNull Response<ArticleList> response) {
                ArticleList list = response.body();


                try{
                    mAdapter= new ArticleAdapter(getActivity(), list.getArticles());
                    listView.setAdapter(mAdapter);
                } catch (NullPointerException e) {
                    Log.v("LOG::", e.toString());
                }

            }


            @Override
            public void onFailure(Call<ArticleList> call, Throwable throwable) {
                Log.d("ERR:", throwable.getMessage() );

            }
        });



        listView.setAdapter(mAdapter);
        // set on click event listener to list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                mAdapter.registerToggle(pos);
            }
        });



        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}