package com.AndyH.volleytools;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class matchHistory extends AppCompatActivity {
    private RecyclerView hmRecyclerView;
    private RecyclerView.Adapter hmAdapter;
    private RecyclerView.LayoutManager hmLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_match_history);

        ArrayList<Game> matchHistoryArrayList = new ArrayList<>();
        matchHistoryArrayList.add(new Game(25,3,1,2,"好人","壞人", Calendar.getInstance()));
        matchHistoryArrayList.add(new Game(24,6,2,3,"好人","壞人", Calendar.getInstance()));
        matchHistoryArrayList.add(new Game(23,9,3,4,"好人","壞人", Calendar.getInstance()));

        hmRecyclerView = findViewById(R.id.mh_recyclerview);
        hmLayoutManager = new LinearLayoutManager(this);
        hmAdapter = new mRecyclerAdapter(matchHistoryArrayList);

        hmRecyclerView.setAdapter(hmAdapter);
        hmRecyclerView.setLayoutManager(hmLayoutManager);

    }
}
