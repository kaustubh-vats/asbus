package com.asbus101.asbus.ui.aboutus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutUsModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AboutUsModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is about us fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}