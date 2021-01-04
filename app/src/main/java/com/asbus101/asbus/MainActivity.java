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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    EditText mEmail, mPassword;
    Button button;
    ImageView imageView;
    TextView textView, textView1, textView2, textView3;
    FirebaseAuth firebaseAuth;
    Animation rightIn, rightOut, leftIn, leftOut, popUp, popOut;
    boolean loginFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        mEmail=findViewById(R.id.editTextTextEmailAddress);
        progressBar=findViewById(R.id.progressBar);
        mPassword=findViewById(R.id.editTextTextPassword);
        textView=findViewById(R.id.textView6);
        textView1=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView5);
        button=findViewById(R.id.button);
        imageView=findViewById(R.id.imageView);
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
        loginFlag = getIntent().getBooleanExtra("loginFlag",true);
        if(!loginFlag)
        {
            textView.setVisibility(View.VISIBLE);
            textView.startAnimation(popUp);
        }
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
                textView.setVisibility(View.VISIBLE);
            }
        }
        textView1.startAnimation(popUp);
        textView2.startAnimation(popUp);
        textView3.startAnimation(popUp);
        button.startAnimation(rightIn);
        mPassword.startAnimation(leftIn);
        mEmail.startAnimation(rightIn);
        imageView.startAnimation(popUp);
    }

    public void registerIntent(View view) {
        if(!loginFlag)
        {
            textView.startAnimation(popOut);
        }
        textView1.startAnimation(popOut);
        textView2.startAnimation(popOut);
        textView3.startAnimation(popOut);
        button.startAnimation(rightOut);
        mPassword.startAnimation(leftOut);
        mEmail.startAnimation(rightOut);
        imageView.startAnimation(popOut);
        Intent intent = new Intent(this,RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void loginUser(View view) {
        String email=mEmail.getText().toString().trim();
        String password =mPassword.getText().toString().trim();
        if(email.isEmpty() || password.isEmpty())
        {
            if(email.isEmpty())
            {
                mEmail.setError("This is is a Required field");
            }
            if(password.isEmpty())
            {
                mPassword.setError("This is a required field");
            }
        }
        else
        {
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        if(firebaseAuth.getCurrentUser().isEmailVerified())
                        {
                            if(!loginFlag)
                            {
                                textView.startAnimation(popOut);
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                            textView1.startAnimation(popOut);
                            textView2.startAnimation(popOut);
                            textView3.startAnimation(popOut);
                            button.startAnimation(rightOut);
                            mPassword.startAnimation(leftOut);
                            mEmail.startAnimation(rightOut);
                            imageView.startAnimation(popOut);
                            Intent intent = new Intent(MainActivity.this,HomeDrawerActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            progressBar.setVisibility(View.INVISIBLE);
                            textView.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Please Verify your Email", Toast.LENGTH_SHORT).show();
                            firebaseAuth.signOut();
                        }
                    }
                    else
                    {
                        progressBar.setVisibility(View.INVISIBLE);
                        System.out.println();
                        if(Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()).contains("email address is badly formatted"))
                        {
                            Toast.makeText(MainActivity.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                        }
                        else if(Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()).contains("There is no user record corresponding to this identifier"))
                        {
                            Toast.makeText(MainActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                        }
                        else if(Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()).contains("The password is invalid"))
                        {
                            Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    public void resetPassword(View view) {
        String email=mEmail.getText().toString().trim();
        if(email.isEmpty())
        {
            mEmail.setError("This is is a Required field");
        }
        else
        {
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(MainActivity.this, "Reset Password Email sent", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }
}