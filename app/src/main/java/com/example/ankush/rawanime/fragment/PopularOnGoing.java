package com.example.ankush.rawanime.fragment;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ankush.rawanime.R;
import com.example.ankush.rawanime.adapters.RecyclerViewAdapter;
import com.gecdevelopers.scrapper.AnimeModel;
import com.gecdevelopers.scrapper.Scraper;

import java.util.ArrayList;
import java.util.List;


public class PopularOnGoing extends Fragment {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<AnimeModel> list;
    ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.anime_list,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list= new ArrayList<>();
        adapter= new RecyclerViewAdapter(list,getContext());
        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        progressBar=view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        MyAsyncTask task= new MyAsyncTask();
        task.execute();
    }

    //asynsc task
    private class MyAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            //since async works on different thread it cannot update the ui so we need to run the updating task on UI thread

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Scraper scrapper= new Scraper();
            scrapper.scrapeForPopularOngoingAnimeList();
            list.addAll(scrapper.getPopularOngoindAnimeList());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            adapter.notifyDataSetChanged();

        }
    }




}
