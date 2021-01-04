package com.asbus101.asbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_TIMEOUT = 4000;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        textView = findViewById(R.id.textView13);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.pop_up_anim);
        imageView=findViewById(R.id.imageView3);
        textView.startAnimation(animation);
        imageView.startAnimation(animation);
        if(firebaseUser != null)
        {
            textView.setText(getString(R.string.welcome_text)+" "+firebaseUser.getDisplayName());
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(firebaseUser != null && firebaseUser.isEmailVerified())
                {
                    Intent intent = new Intent(SplashScreen.this,HomeDrawerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_TIMEOUT);
    }
}