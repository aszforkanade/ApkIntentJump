package com.pyystone.apkintentjump.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pyystone.apkintentjump.R;
import com.pyystone.apkintentjump.data.JumpHost;
import com.pyystone.apkintentjump.data.JumpScheme;
import com.pyystone.apkintentjump.ui.HostListAdapter;

public class SchemeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static final String EXTRA_SCHEME_ID = "extra_scheme_uuid";

    private int mSchemeid;
    private ListView mListView;
    private HostListAdapter mHostListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme);
        mSchemeid = getIntent().getIntExtra(EXTRA_SCHEME_ID,-1);
        if (mSchemeid == -1) {
            finish();
            return;
        }
        initUI();
    }

    private void initUI() {
        JumpScheme scheme = JumpScheme.load(mSchemeid);
        mListView = (ListView) findViewById(R.id.listView);
        mHostListAdapter = new HostListAdapter(this,scheme);
        mListView.setAdapter(mHostListAdapter);
        initListener();
    }

    private void initListener() {
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        JumpHost host = (JumpHost) mHostListAdapter.getItem(position);
        Intent intent = new Intent(this,HostActivity.class);
        intent.putExtra(HostActivity.EXTRA_HOST_ID,host.getId());
        startActivity(intent);
    }
}
