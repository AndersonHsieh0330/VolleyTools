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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.auth.api.Auth;
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
    Activity current_Activity;
    ImageButton exit_imgbutton;
    Button login_button;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    SignInButton googleSignInButton;
    Button signOutButton;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;
    FirebaseUser currentUser;
    private boolean isloggedin;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mInflater = inflater;
        mContainer = container;
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        isloggedin = checkLogin(currentUser);
        View inflatedView= inflateView( inflater, container);
        createRequest();
        button_initialization(inflatedView);

        return inflatedView;
    }

    @Override
    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
    }

    private View inflateView(LayoutInflater inflater, ViewGroup container){
        if(!isloggedin){
            return inflater.inflate(R.layout.google_login_page, container, false);
        }else{
            return inflater.inflate(R.layout.google_logout_page, container, false);
        }
    }

    private boolean checkLogin(FirebaseUser currentUser){
        if(currentUser!= null){
            return true;
        }else{
            return false;
        }
    }


    private void button_initialization(View inflatedView) {
        //exit_imgbutton exist regardless of the login state
        exit_imgbutton = inflatedView.findViewById(R.id.exit_imgbutton);
        enableExitButton();

        if(!isloggedin){
            googleSignInButton = (SignInButton) inflatedView.findViewById(R.id.gsign_in_button);
            googleSignInButton.setSize(SignInButton.SIZE_STANDARD);
            googleSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn();
                    Log.d("firebase", "onClick: "+currentUser);


                }
            });

        } else{

            signOutButton = inflatedView.findViewById(R.id.logout_button);
            signOutButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mAuth.signOut();
                    mGoogleSignInClient.signOut();
                    isloggedin = false;
                   removeLoginPage();
                   Toast.makeText(getContext(),R.string.logout_sucess,Toast.LENGTH_LONG).show();

                }
            });
        }

    }
    private void disableExitButton(){
        exit_imgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do nothing
            }

        });
    }
    private void enableExitButton(){
        exit_imgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLoginPage();
            }

        });
    }
    public void removeLoginPage(){
        disableExitButton();
        FragmentManager fmanager = getParentFragmentManager();
        fmanager.beginTransaction().remove(fmanager.findFragmentByTag(MainActivity.LOGIN_FRAGMENT_TAG)).commit();

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
        window.setLayout((int) (width * 0.7), (int) (height * 0.55));
        window.setGravity(Gravity.CENTER);

        //diable closing by clicking outside of dialog fragment
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
    }

    private void createRequest(){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this.getContext(),gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("firebase", " currnet user statis:" );
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                disableExitButton();
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("firebase", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                enableExitButton();
                Log.w("firebase", "Google sign in failed", e);
            }
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("firebase", "signInWithCredential:success");
                            currentUser = mAuth.getCurrentUser();
                            isloggedin = true;
                            Toast.makeText(getContext(),R.string.login_sucess_NOLINEBREAK,Toast.LENGTH_LONG).show();
                            removeLoginPage();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("firebase", "signInWithCredential:failure", task.getException());

                        }
                    }
                });

    }

}
