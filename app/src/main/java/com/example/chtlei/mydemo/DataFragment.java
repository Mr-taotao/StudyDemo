package com.example.chtlei.mydemo;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by chtlei on 18-10-30.
 */

public class DataFragment extends Fragment {
    // data object we want to retain
    private String[] data;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String[] getData() {
        return data;
    }
}
