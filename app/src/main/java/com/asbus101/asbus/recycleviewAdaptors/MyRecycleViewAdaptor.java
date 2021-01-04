package com.asbus101.asbus.recycleviewAdaptors;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asbus101.asbus.R;
import com.asbus101.asbus.ServicesDisplayActivity;
import com.asbus101.asbus.models.ServiceModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MyRecycleViewAdaptor extends RecyclerView.Adapter<MyRecycleViewAdaptor.MyViewHolder> {
    List<ServiceModel> serviceModels;
    Context context;

    public MyRecycleViewAdaptor(List<ServiceModel> serviceModels, Context context) {
        this.serviceModels = serviceModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.services_temp,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ServiceModel serviceModel=serviceModels.get(position);
        holder.textView.setText(serviceModel.getTitle());
        holder.textView.setSelected(true);
        holder.imageView.setImageResource(serviceModel.getImage());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ServicesDisplayActivity.class);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAppInstalled())
                {
                    String name= FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    String message="Hello! My name is "+name+" and I would like to buy the service named "+serviceModel.getTitle();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=919510342595&text="+message));
                    context.startActivity(intent);
                }
                else
                {
                    Toast.makeText(context, "You need to install Whatsapp to complete the action", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkAppInstalled() {

        PackageManager packageManager = context.getPackageManager();
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

    @Override
    public int getItemCount() {
        return serviceModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        Button button;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView11);
            button=itemView.findViewById(R.id.button4);
            imageView=itemView.findViewById(R.id.imageView5);
        }

    }
}
