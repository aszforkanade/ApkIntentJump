package com.pyystone.apkintentjump.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pyystone.apkintentjump.R;
import com.pyystone.apkintentjump.data.JumpScheme;

public class SchemeActivity extends AppCompatActivity {
    public static final String EXTRA_SCHEME_UUID = "extra_scheme_uuid";

    private int mSchemeUuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme);
        mSchemeUuid = getIntent().getIntExtra(EXTRA_SCHEME_UUID,-1);
        if (mSchemeUuid == -1) {
            finish();
            return;
        }
        initUI();
    }

    private void initUI() {
        JumpScheme scheme = JumpScheme.load(mSchemeUuid);
    }
}
