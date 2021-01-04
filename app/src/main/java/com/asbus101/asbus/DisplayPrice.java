package com.asbus101.asbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class DisplayPrice extends AppCompatActivity {
    String name;
    String title;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_price);
        title=getIntent().getStringExtra("title");
        textView=findViewById(R.id.textView12);
        textView.setText(title);
        name= FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    }

    public void sendWhatsApp(View view) {
        if(checkAppInstalled())
        {
            String message="Hello! My name is "+name+" and I would like to buy the service named "+title;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=919510342595&text="+message));
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "You need to install Whatsapp to complete the action", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkAppInstalled() {
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        try{
            packageManager.getPackageInfo("com.whatsapp",PackageManager.GET_ACTIVITIES);
            app_installed=true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed=false;
            e.printStackTrace();
        }
        return app_installed;
    }
}