package com.asbus101.asbus;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ServiceDisplayFragment extends Fragment {
    String title;
    int image;
    TextView textView, textView1;
    ImageView imageView;
    Animation rightIn, leftIn, popUp;
    Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_display, container, false);
        title = getArguments().getString("title");
        image = getArguments().getInt("image");
        textView = view.findViewById(R.id.titleText);
        textView1 = view.findViewById(R.id.textView14);
        imageView = view.findViewById(R.id.myImageView);
        button = view.findViewById(R.id.buttonBuyNow1);
        rightIn = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_right_in);
        leftIn = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_left_in);
        popUp = AnimationUtils.loadAnimation(getActivity(),R.anim.pop_up_anim);
        rightIn.setDuration(1000);
        leftIn.setDuration(1000);
        popUp.setDuration(1000);
        textView.setText(title);
        textView1.setText("About the package "+title);
        textView.setSelected(true);
        imageView.setImageResource(image);
        textView.startAnimation(leftIn);
        textView1.startAnimation(rightIn);
        imageView.startAnimation(popUp);
        button.startAnimation(popUp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAppInstalled())
                {
                    String name= FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    String message="Hello! My name is "+name+" and I would like to buy the service named "+title;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=919510342595&text="+message));
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getActivity(), "You need to install Whatsapp to complete the action", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    private boolean checkAppInstalled() {

        PackageManager packageManager = getActivity().getPackageManager();
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