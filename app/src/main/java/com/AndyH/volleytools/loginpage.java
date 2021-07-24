package com.AndyH.volleytools;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

public class loginpage extends DialogFragment {
    private static final int RC_SIGN_IN = 12345;
    Activity current_Activity = this.getActivity();
    Fragment context_fragment = this;
    ImageButton exit_imgbutton;
    Button login_button;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    SignInButton googleSignInButton;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v=   inflater.inflate(R.layout.google_login_page,container,false);
        mAuth = FirebaseAuth.getInstance();

        this.button_initialization(v);
        createRequest();

        googleSignInButton = (SignInButton)v.findViewById(R.id.gsign_in_button);
        googleSignInButton.setSize(SignInButton.SIZE_ICON_ONLY);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        Button button = v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FirebaseAuth.getInstance().signOut();
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

       FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    private void button_initialization(View view){
        exit_imgbutton = view.findViewById(R.id.exit_imgbutton);
        exit_imgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fmanager= getParentFragmentManager();
                fmanager.beginTransaction().remove(fmanager.findFragmentByTag("login_page_dialfrag")).commit();
            }

        });

        login_button=view.findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;
        int height = size.y;
        //window.setLayout((int) (width * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setLayout((int) (width * 0.75), (int)(height*0.75));
        window.setGravity(Gravity.CENTER);
    }
    private void createRequest(){
        //create google sign in request
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        //build a client and pass sign in request to client
        mGoogleSignInClient = GoogleSignIn.getClient(this.getActivity(), gso);
        Log.d("FirebaseLogs","clientget");
    }

    private void signIn() {
        //start the signin activity on button click
        Log.d("FirebaseLogs","signinintent");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        this.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("FirebaseLogs","Activity result1");


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
                Log.d("FirebaseLogs","Activity result1"+account.getId());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                if(e!=null){
                Toast toast = Toast.makeText(current_Activity,e.getMessage(),Toast.LENGTH_SHORT);}
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        //pass credentials to the instance of firebase authenticaation
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("e","mi");
                            Toast.makeText(getContext(), "Firebase Login Success" + task.getException(), Toast.LENGTH_SHORT);
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "Firebase Login Failed" + task.getException(), Toast.LENGTH_SHORT);
                        }
                    }
                });
    }


}
