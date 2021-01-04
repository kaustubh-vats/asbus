package com.asbus101.asbus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    ProgressBar progressBar;
    EditText mEmail, mPassword, mName;
    FirebaseAuth firebaseAuth;
    Button button;
    ImageView imageView;
    TextView textView, textView1;
    Animation rightIn, rightOut, leftIn, leftOut, popUp, popOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        mEmail=findViewById(R.id.editTextTextEmailAddress2);
        progressBar=findViewById(R.id.progressBar2);
        mPassword=findViewById(R.id.editTextTextPassword2);
        mName=findViewById(R.id.editTextTextPersonName);
        button=findViewById(R.id.button2);
        imageView=findViewById(R.id.imageView2);
        textView=findViewById(R.id.textView3);
        textView1=findViewById(R.id.textView4);
        rightIn = AnimationUtils.loadAnimation(this,R.anim.slide_right_in);
        rightOut = AnimationUtils.loadAnimation(this,R.anim.slide_right_out);
        leftIn = AnimationUtils.loadAnimation(this,R.anim.slide_left_in);
        leftOut = AnimationUtils.loadAnimation(this,R.anim.slide_left_out);
        popUp = AnimationUtils.loadAnimation(this,R.anim.pop_up_anim);
        popOut = AnimationUtils.loadAnimation(this,R.anim.pop_out_anim);
        rightIn.setDuration(1000);
        rightOut.setDuration(1000);
        leftIn.setDuration(1000);
        leftOut.setDuration(1000);
        popUp.setDuration(1000);
        popOut.setDuration(1000);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null)
        {
            if(firebaseAuth.getCurrentUser().isEmailVerified())
            {
                Intent intent = new Intent(this,HomeDrawerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(this, "Please verify your Email and login again", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.putExtra("loginFlag",false);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }
        textView.startAnimation(popUp);
        textView1.startAnimation(popUp);
        button.startAnimation(rightIn);
        mPassword.startAnimation(leftIn);
        mEmail.startAnimation(rightIn);
        mName.startAnimation(leftIn);
        imageView.startAnimation(popUp);
    }

    public void loginIntent(View view) {
        textView.startAnimation(popOut);
        textView1.startAnimation(popOut);
        button.startAnimation(rightOut);
        mPassword.startAnimation(leftOut);
        mEmail.startAnimation(rightOut);
        mName.startAnimation(leftOut);
        imageView.startAnimation(popOut);
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void RegisterUser(View view) {
        String email=mEmail.getText().toString().trim();
        String password =mPassword.getText().toString().trim();
        String name = mName.getText().toString().trim();
        if(email.isEmpty() || password.isEmpty())
        {
            if(name.isEmpty())
            {
                mName.setError("This is is a Required field");
            }
            if(email.isEmpty())
            {
                mEmail.setError("This is is a Required field");
            }
            if(password.isEmpty())
            {
                mPassword.setError("This is a required field");
            }
            return;
        }
        else
        {
            if(password.length()<8)
            {
                mPassword.setError("Minimum length of password is 8 characters");
            }
            else
            {
                Pattern pattern;
                Matcher matcher;
                final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
                pattern = Pattern.compile(PASSWORD_PATTERN);
                matcher = pattern.matcher(password);
                if(!matcher.matches())
                {
                    mPassword.setError("Password must be a combination of Alphabets, Numbers and Special character");
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                firebaseUser.updateProfile(userProfileChangeRequest);
                                firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        textView.startAnimation(popOut);
                                        textView1.startAnimation(popOut);
                                        button.startAnimation(rightOut);
                                        mPassword.startAnimation(leftOut);
                                        mEmail.startAnimation(rightOut);
                                        mName.startAnimation(leftOut);
                                        imageView.startAnimation(popOut);
                                        firebaseAuth.signOut();
                                        Toast.makeText(RegisterActivity.this, "Verification Email sent", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.putExtra("loginFlag",false);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(RegisterActivity.this, "Failed to send Verification Email", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(RegisterActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }
    }
}