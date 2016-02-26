package com.pyystone.apkintentjump.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pyystone.apkintentjump.AppTools;
import com.pyystone.apkintentjump.JumpTools;
import com.pyystone.apkintentjump.R;
import com.pyystone.apkintentjump.data.JumpHost;
import com.pyystone.apkintentjump.data.JumpScheme;
import com.pyystone.apkintentjump.ui.HostListAdapter;

public class SchemeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static final String EXTRA_SCHEME_ID = "extra_scheme_uuid";

    private int mSchemeid;
    private JumpScheme mScheme;
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
        mScheme = JumpScheme.load(mSchemeid);
        mListView = (ListView) findViewById(R.id.listView);
        mHostListAdapter = new HostListAdapter(this,mScheme);
        mListView.setAdapter(mHostListAdapter);
        initListener();
    }

    private void initListener() {
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        JumpHost host = (JumpHost) mHostListAdapter.getItem(position);
        if (host.getParams().size() > 0) {
            Intent intent = new Intent(this, HostActivity.class);
            intent.putExtra(HostActivity.EXTRA_HOST_ID, host.getId());
            startActivity(intent);
        } else {
            JumpTools.urlJump(this,mScheme.getScheme() + "://" + host.getHost());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppTools.getInstance().toast("result:" + (resultCode == RESULT_OK ? "OK" : "CANCEL"));
    }
}
