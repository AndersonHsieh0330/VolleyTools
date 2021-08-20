package com.AndyH.volleytools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class mRecyclerAdapter extends RecyclerView.Adapter<mRecyclerAdapter.mViewHolder> {
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

    public mRecyclerAdapter(ArrayList<Game> matchHistoryArrayList){

        mhAL = matchHistoryArrayList;
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd 'at' hh:mm aa");
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

        Game current_game = mhAL.get(position);
        holder.bSets.setText(String.valueOf(current_game.getBadpeople_sets()));
        holder.bName.setText(current_game.getBadpeople_teamname());
        holder.bScore.setText(String.valueOf(current_game.getBadpeople_points()));
        holder.gSets.setText(String.valueOf(current_game.getGoodpeople_sets()));
        holder.gName.setText(current_game.getGoodpeople_teamname());
        holder.gScore.setText(String.valueOf(current_game.getGoodpeople_points()));
        holder.time.setText(simpleDateFormat.format(current_game.getCalendar().getTime()));

    }

    @Override
    public int getItemCount() {
        return mhAL.size();
    }
}
