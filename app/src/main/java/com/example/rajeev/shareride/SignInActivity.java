package com.example.rajeev.shareride;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.rajeev.shareride.models.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by rajeev on 4/3/18.
 */

public class SignInActivity extends BasicActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";
    private static int RC_SIGN_IN = 100;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mSignInButton;
    private Button mSignUpButton;
    private Button mGoogleSignInButton;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_in);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        float density = getResources().getDisplayMetrics().density;
        float dpHeight = displayMetrics.heightPixels / density;
        float dpWidth = displayMetrics.widthPixels / density;
//        Log.e("Width", String.valueOf(size.x));
//        Toast.makeText(this,   " Width "+ String.valueOf(dpWidth)+ " Height " + String.valueOf(dpHeight), Toast.LENGTH_LONG).show();

        mGoogleSignInButton = findViewById(R.id.login_with_google);

        // To position button at runtime
        RelativeLayout.LayoutParams mSignInButton = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mSignInButton.leftMargin = (int) (dpWidth*0.4);
        mSignInButton.topMargin = (int) (dpHeight*1.3);
        mGoogleSignInButton.setLayoutParams(mSignInButton);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("YOUR_TOKEN")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
        .build();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        // Uncomment for Development
//        mEmailField = findViewById(R.id.field_email);
//        mPasswordField = findViewById(R.id.field_password);
//        mSignInButton = findViewById(R.id.button_sign_in);
//        mSignUpButton = findViewById(R.id.button_sign_up);

        // Click listeners
        mGoogleSignInButton.setOnClickListener(this);
//        mSignInButton.setOnClickListener(this);
//        mSignUpButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(mAuth.getCurrentUser());
        }
    }

    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (requestCode == RC_SIGN_IN) {
                @SuppressLint("RestrictedApi") Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task, result);
            }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask, GoogleSignInResult result) {
        try{
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account, result);

        } catch (ApiException e) {
            e.printStackTrace();
            Log.w(TAG, "Google sign in failed", e);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account, final GoogleSignInResult result) {
        Log.d(TAG, "firebaseAuthWithGoogle : " + account.getId());

        showProgressDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential;success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            onGoogleAuthSuccess(user, result);
                        }
                        else {
                            Log.w(TAG, "signInWithCredentialsFailure:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void onGoogleAuthSuccess(FirebaseUser user, GoogleSignInResult result) {

        writeNewUser(user.getUid(), result.getSignInAccount().getDisplayName(), user.getEmail() );
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();

    }

//    private void signIn() {
//        Log.d(TAG, "signIn");
//        if (!validateForm()) {
//            return;
//        }
//
//        showProgressDialog();
//        String email = mEmailField.getText().toString();
//        String password = mPasswordField.getText().toString();
//
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
//                        hideProgressDialog();
//
//                        if (task.isSuccessful()) {
//                            onAuthSuccess(task.getResult().getUser());
//                        } else {
//                            Toast.makeText(SignInActivity.this, "Sign In Failed",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

//    private void signUp() {
//        Log.d(TAG, "signUp");
//        if (!validateForm()) {
//            return;
//        }
//
//        showProgressDialog();
//        String email = mEmailField.getText().toString();
//        String password = mPasswordField.getText().toString();
//
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
//                        hideProgressDialog();
//
//                        if (task.isSuccessful()) {
//                            onAuthSuccess(task.getResult().getUser());
//                        } else {
//                            Toast.makeText(SignInActivity.this, "Sign Up Failed",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//
    private void onAuthSuccess(FirebaseUser user) {
        String username = (user.getDisplayName());

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }
//
//    private String usernameFromEmail(String email) {
//        if (email.contains("@")) {
//            return email.split("@")[0];
//        } else {
//            return email;
//        }
//    }
//
//    private boolean validateForm() {
//        boolean result = true;
//        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
//            mEmailField.setError("Required");
//            result = false;
//        } else {
//            mEmailField.setError(null);
//        }
//
//        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
//            mPasswordField.setError("Required");
//            result = false;
//        } else {
//            mPasswordField.setError(null);
//        }
//
//        return result;
//    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.login_with_google){
            signInWithGoogle();
        }
//        else if (i == R.id.button_sign_in) {
//            signIn();
//        } else if (i == R.id.button_sign_up) {
//            signUp();
//        }
    }
}
