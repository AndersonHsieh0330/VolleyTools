package com.AndyH.volleytools;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPage extends DialogFragment {
    private static final int RC_SIGN_IN = 12345;
    private ImageButton exit_imgbutton;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        initializeFirebaseAssociateReference();
        View inflatedView = inflateView(inflater, container);
        createRequest();
        BindViewsAndListeners(inflatedView);


        return inflatedView;
    }

    @Override
    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
    }

    private View inflateView(LayoutInflater inflater, ViewGroup container) {
        if (currentUser == null) {
            return inflater.inflate(R.layout.google_login_page, container, false);
        } else {
            return inflater.inflate(R.layout.google_logout_page, container, false);
        }
    }

    private void BindViewsAndListeners(View inflatedView) {
        //exit_imgbutton exist regardless of the login state
        exit_imgbutton = inflatedView.findViewById(R.id.exit_imgbutton);
        enableExitButton();

        if (currentUser == null) {
            SignInButton googleSignInButton = (SignInButton) inflatedView.findViewById(R.id.gsign_in_button);
            googleSignInButton.setSize(SignInButton.SIZE_STANDARD);
            googleSignInButton.setOnClickListener(v -> {
                signIn();
                Log.d("firebase", "onClick: " + currentUser);


            });

        } else {

            Button signOutButton = inflatedView.findViewById(R.id.logout_button);
            signOutButton.setOnClickListener(v -> {
                mAuth.signOut();
                mGoogleSignInClient.signOut();
                removeFragment();
                Toast.makeText(getContext(), R.string.logout_sucess, Toast.LENGTH_LONG).show();

            });
        }

    }

    private void disableExitButton() {
        exit_imgbutton.setOnClickListener(v -> {
            //do nothing
        });
    }

    private void enableExitButton() {
        exit_imgbutton.setOnClickListener(v -> removeFragment());
    }

    private void removeFragment() {
        disableExitButton();
        FragmentManager currentFragManager = getParentFragmentManager();
        Fragment currentFrag = currentFragManager.findFragmentByTag(MainActivity.LOGIN_FRAGMENT_TAG);
        if (currentFrag != null) {
            currentFragManager.beginTransaction().remove(currentFrag).commit();
        } else {
            Log.d("log", "removeFragment: currentFrag not found");
        }
    }

    private void setDialogSize() {
        int currentOrientation = getResources().getConfiguration().orientation;
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(false);
            Window window = dialog.getWindow();
            if (window != null) {
                Point size = new Point();
                Display display = window.getWindowManager().getDefaultDisplay();
                display.getSize(size);

                int width = size.x;
                int height = size.y;

                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    window.setLayout((int) (width * 0.7), (int) (height * 0.65));
                } else {
                    window.setLayout((int) (width * 0.7), (int) (height * 0.45));
                }
                window.setGravity(Gravity.CENTER);
            } else {
                Log.d("log", "setWindowSize: window not found");
            }

        } else {
            Log.d("log", "setCanceledOnTouchOutside: dialog not found");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        setDialogSize();
    }

    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        if (this.getContext() != null) {
            mGoogleSignInClient = GoogleSignIn.getClient(this.getContext(), gso);
        } else {
            Log.d("log", "getGoogleSignInClient: no Context");
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("firebase", " currnet user status:");
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                disableExitButton();
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account!=null) {
                    Log.d("firebase", "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                }else{
                    Log.d("firebase", "GoogleSignInAccount: null");
                }
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("firebase", "Google sign in failed", e);
                Toast.makeText(getContext(), R.string.loginFailed, Toast.LENGTH_SHORT).show();
                enableExitButton();

            }
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("firebase", "signInWithCredential:success");
                        currentUser = mAuth.getCurrentUser();
                        Toast.makeText(getContext(), R.string.login_sucess_NOLINEBREAK, Toast.LENGTH_LONG).show();
                        removeFragment();

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("firebase", "signInWithCredential:failure", task.getException());

                    }
                });

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void initializeFirebaseAssociateReference() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }


}
