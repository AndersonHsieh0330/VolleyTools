package com.AndyH.volleytools;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MHRecyclerAdapter extends RecyclerView.Adapter<MHRecyclerAdapter.mViewHolder> {
    private ArrayList<Game> mhAL;
    private SimpleDateFormat simpleDateFormat;

    public static class mViewHolder extends RecyclerView.ViewHolder{
        public TextView bSets, bName, bScore,gSets,gName,gScore,time;
        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            bSets = itemView.findViewById(R.id.hm_badpeople_textview_sets);
            bName = itemView.findViewById(R.id.hm_badpeople);
            bScore = itemView.findViewById(R.id.hm_badpeople_textview_score);
            gSets = itemView.findViewById(R.id.hm_goodpeople_textview_sets);
            gName = itemView.findViewById(R.id.hm_goodpeople);
            gScore = itemView.findViewById(R.id.hm_goodpeople_textview_score);
            time = itemView.findViewById(R.id.hm_textivew_time);

        }


    }

    public MHRecyclerAdapter(ArrayList<Game> matchHistoryArrayList){

        mhAL = matchHistoryArrayList;
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm aa");
    }


    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_match_elements,parent,false);
        mViewHolder vh = new mViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        //newest history game record is on top
        Game current_game = mhAL.get((mhAL.size()-1)-position);


        holder.bSets.setText(String.valueOf(current_game.getBadpeople_sets()));
        holder.bScore.setText(String.valueOf(current_game.getBadpeople_points()));
        holder.bName.setText(current_game.getBadpeople_teamname());
        holder.gSets.setText(String.valueOf(current_game.getGoodpeople_sets()));
        holder.gScore.setText(String.valueOf(current_game.getGoodpeople_points()));
        holder.gName.setText(current_game.getGoodpeople_teamname());
        holder.time.setText(current_game.getGameEndTime());

    }

    @Override
    public int getItemCount() {
        return mhAL.size();
    }

    private void generateColorPatter(){}
}