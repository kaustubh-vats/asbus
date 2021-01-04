package com.asbus101.asbus.viewPagerAdaptor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.asbus101.asbus.ServiceDisplayFragment;
import com.asbus101.asbus.models.ServiceModel;

import java.util.List;

public class ServiceViewPagerAdaptor extends FragmentStateAdapter {
    List<ServiceModel> serviceModels;
    public ServiceViewPagerAdaptor(@NonNull FragmentActivity fragmentActivity, List<ServiceModel> serviceModels) {
        super(fragmentActivity);
        this.serviceModels = serviceModels;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("title",serviceModels.get(position).getTitle());
        bundle.putInt("image",serviceModels.get(position).getImage());
        ServiceDisplayFragment serviceDisplayFragment = new ServiceDisplayFragment();
        serviceDisplayFragment.setArguments(bundle);
        return serviceDisplayFragment;
    }

    @Override
    public int getItemCount() {
        return serviceModels.size();
    }
}
