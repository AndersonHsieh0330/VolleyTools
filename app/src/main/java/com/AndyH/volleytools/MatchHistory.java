package com.AndyH.volleytools;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MatchHistory extends AppCompatActivity {
    private ArrayList<Game> matchHistoryArrayList;
    private MHRecyclerAdapter hmAdapter;
    private DatabaseReference currentUserHistoryGameRef;
    private HashMap<Integer,Integer> colorPattern;
    private int colorIndex;


    private final ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            //callback is triggered once for each exiting child

            Game game = snapshot.getValue(Game.class);
            if(game != null){
                game.setFbKey(snapshot.getKey());
                game.setBackgroundResource(generateBackgroundResource());
            }
            Log.d("childEvent", "onChildAdd: "+snapshot.getKey());
            matchHistoryArrayList.add(game);
            hmAdapter.notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            //locate the deleted history game with unique key
            int oldSize = matchHistoryArrayList.size();
            int oldPosition = findIndex(matchHistoryArrayList,snapshot.getKey());
            Log.d("childEvent", "onChildRemoved: going to delete "+snapshot.getKey()+" at position "+ oldPosition);


            for(int i=0;i<matchHistoryArrayList.size();i++){
                Log.d("childEvent", "onChildRemoved: currently has "+ matchHistoryArrayList.get(i).getFbKey()+"at index "+ i);
            }
            matchHistoryArrayList.remove(oldPosition);

            //the list in the adapter is reversed, thus we pass in the reversed position index.
            hmAdapter.notifyItemRemoved((oldSize-1)-oldPosition);

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.Mint_Green));


        setContentView(R.layout.activity_match_history);
        initializeFirebaseAssociateReference();
        BindViewsAndListensers();
        colorPattern = initializeColorMap();
        colorIndex=0;

        matchHistoryArrayList = new ArrayList<>();
        currentUserHistoryGameRef.addChildEventListener(childEventListener);

        RecyclerView hmRecyclerView = findViewById(R.id.mh_recyclerview);
        RecyclerView.LayoutManager hmLayoutManager = new LinearLayoutManager(this);
        hmAdapter = new MHRecyclerAdapter(matchHistoryArrayList);
        hmRecyclerView.setAdapter(hmAdapter);
        hmRecyclerView.setLayoutManager(hmLayoutManager);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (hmAdapter != null) {
            hmAdapter.saveStates(outState);
        }
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        if (hmAdapter != null) {
            hmAdapter.restoreStates(savedInstanceState);


        }
    }

    private void initializeFirebaseAssociateReference(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = firebaseDatabase.getReference();
        if(currentUser !=null){
            DatabaseReference currentUserRef = rootRef.child(currentUser.getUid());
            currentUserHistoryGameRef = currentUserRef.child("historyGames");
        }
        else{
            Log.d("saveorrestart", "user not logged in");
        }
    }

    private int findIndex(ArrayList<Game> arrayList, String key){
        for(int i = 0; i<arrayList.size();i++){
            if(key.equals(arrayList.get(i).getFbKey())){
                Log.d("childeventandy", "findIndex: "+ i);
                return i;
            }
        }


        //should never get here
        return -1;
    }

    private HashMap<Integer,Integer> initializeColorMap(){
        HashMap<Integer,Integer> colorMap = new HashMap<>();
        colorMap.put(1, R.drawable.matchhistory_page_elements_design_blossompink);//sakura_pink
        colorMap.put(2,R.drawable.matchhistory_page_elements_design_yellowcrayola);
        colorMap.put(3,R.drawable.matchhistory_page_elements_design_mintgreen);
        colorMap.put(4,R.drawable.matchhistory_page_elements_design_azureblue);
        colorMap.put(5,R.drawable.matchhistory_page_elements_design_maroonpurple);

        return colorMap;
    }

    private int generateBackgroundResource(){
        colorIndex++;
        if(colorIndex==6){
            colorIndex=1;
        }

        Integer colorResource = colorPattern.get(colorIndex);
        if(colorResource !=null){
            return colorResource;
        }else{
            Log.d("log", "generateBackgroundResource: unable to find ");
            return R.drawable.matchhistory_page_elements_design_blossompink;
        }
    }
    private void BindViewsAndListensers(){
        ImageButton infoButton = this.findViewById(R.id.mh_buttonInfo);
        infoButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle(R.string.littleNote);
            builder.setMessage(R.string.swipeLeftToDeleteHint);
            builder.create().show();
        });

        ImageButton leaveButton = this.findViewById(R.id.mh_buttonLeave);
        leaveButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            startActivity(intent);

        });
    }

}
