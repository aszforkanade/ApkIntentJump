package com.pyystone.apkintentjump.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pyystone.apkintentjump.R;

public class HostActivity extends AppCompatActivity {

    public static final String EXTRA_HOST_UUID = "extra_scheme_uuid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
    }
}
