package com.asbus101.asbus.ui.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ServicesFragmentModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ServicesFragmentModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Service fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}