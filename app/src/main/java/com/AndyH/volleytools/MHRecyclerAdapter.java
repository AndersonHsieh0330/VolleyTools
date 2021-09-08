package com.AndyH.volleytools;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MHRecyclerAdapter extends RecyclerView.Adapter<MHRecyclerAdapter.mViewHolder> {
    private ArrayList<Game> mhAL;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();//from chthai64 library
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference rootRef;
    private DatabaseReference currentUserRef;
    private DatabaseReference currentUserHistoryGameRef;




    public static class mViewHolder extends RecyclerView.ViewHolder{
        private TextView bSets, bName, bScore,gSets,gName,gScore,time;
        private SwipeRevealLayout swipeRevealLayout;
        private Button deleteButton;
        private RelativeLayout relativeLayout;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.hmContainer);
            swipeRevealLayout = itemView.findViewById(R.id.hm_swipLayout);
            deleteButton = itemView.findViewById(R.id.hmButton_Delete);
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
        initializeFirebaseAssociateReference();


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

        viewBinderHelper.setOpenOnlyOne(false);
        viewBinderHelper.bind(holder.swipeRevealLayout, current_game.getFbKey());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int oldPosition = holder.getAdapterPosition();
                String keyOfRemovedGame = mhAL.get((mhAL.size()-1)-oldPosition).getFbKey();
                currentUserHistoryGameRef.child(keyOfRemovedGame).removeValue();
            }
        });

        holder.relativeLayout.setBackground(ContextCompat.getDrawable(holder.relativeLayout.getContext(),current_game.getBackgroundResource()));
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

    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
        //used to save opening/closing state of swipeViewLayout
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
        //used to save opening/closing state of swipeViewLayout
    }

    private void initializeFirebaseAssociateReference(){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        firebaseDatabase= FirebaseDatabase.getInstance();
        rootRef = firebaseDatabase.getReference();
        if(currentUser!=null){
            currentUserRef = rootRef.child(currentUser.getUid().toString());
            currentUserHistoryGameRef = currentUserRef.child("historyGames");
        }
        else{
            Log.d("saveorrestart", "user not logged in");
        }
    }




}



