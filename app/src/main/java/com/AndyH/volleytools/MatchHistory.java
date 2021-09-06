package com.AndyH.volleytools;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import java.util.Calendar;

public class MatchHistory extends AppCompatActivity {
    private RecyclerView hmRecyclerView;
    private ArrayList<Game> matchHistoryArrayList;
    private RecyclerView.Adapter hmAdapter;
    private RecyclerView.LayoutManager hmLayoutManager;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private boolean isLoggedIn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference rootRef;
    private DatabaseReference currentUserRef;
    private DatabaseReference currentUserHistoryGameRef;
    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            //callback is triggered once for each exiting child

            Game game = snapshot.getValue(Game.class);
            game.setFbKey(snapshot.getKey());
            matchHistoryArrayList.add(game);

            hmAdapter.notifyDataSetChanged();
            Log.d("childeventandy", "onChildAdded: ");
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            //locate the deleted history game with unique key
            int oldPosition = findIndex(matchHistoryArrayList,snapshot.getKey());

            Log.d("childeventandy", "onChildRemoved: "+String.valueOf(matchHistoryArrayList.remove(oldPosition)));
            hmAdapter.notifyItemRemoved(oldPosition);
            Log.d("childeventandy", "onChildRemoved: ");

            for(Game each : matchHistoryArrayList){
                Log.d("childeventandy", "onChildRemoved: "+String.valueOf(each.getBadpeople_points()));
            }

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
    private ItemTouchHelper.SimpleCallback simpCallBack  = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        int position;
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            position = viewHolder.getAdapterPosition();

            String keyOfRemovedGame = matchHistoryArrayList.get(position).getFbKey();
            currentUserHistoryGameRef.child(keyOfRemovedGame).removeValue();

            //duplicate with on child removed, must be tested
//            matchHistoryArrayList.remove(position);
//            hmAdapter.notifyItemRemoved(position);

        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_history);
        initializeFirebaseAssociateReference();

        matchHistoryArrayList = new ArrayList<>();

        hmRecyclerView = findViewById(R.id.mh_recyclerview);
        hmLayoutManager = new LinearLayoutManager(this);
        hmAdapter = new MHRecyclerAdapter(matchHistoryArrayList);

        hmRecyclerView.setAdapter(hmAdapter);
        hmRecyclerView.setLayoutManager(hmLayoutManager);

        currentUserHistoryGameRef.addChildEventListener(childEventListener);
        new ItemTouchHelper(simpCallBack).attachToRecyclerView(hmRecyclerView);
    }


    private void initializeFirebaseAssociateReference(){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        isLoggedIn = (currentUser != null);

        firebaseDatabase= FirebaseDatabase.getInstance();
        rootRef = firebaseDatabase.getReference();
        if(isLoggedIn){
            currentUserRef = rootRef.child(currentUser.getUid().toString());
            currentUserHistoryGameRef = currentUserRef.child("historyGames");
        }
        else{
            Log.d("saveorrestart", "user not logged in");
        }
    }

    private int findIndex(ArrayList<Game> arrayList, String key){
        for(int i = 0; i<arrayList.size();i++){
            if(arrayList.get(0).getFbKey()==key){
                return i;
            }
        }
        //should never get here
        return -1;
    }

}
