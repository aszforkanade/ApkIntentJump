package com.pyystone.apkintentjump.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pyystone.apkintentjump.AppTools;
import com.pyystone.apkintentjump.JumpTools;
import com.pyystone.apkintentjump.R;
import com.pyystone.apkintentjump.data.JumpDataManager;
import com.pyystone.apkintentjump.data.JumpScheme;
import com.pyystone.apkintentjump.ui.SchemeListAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView mListView;
    private SchemeListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initUI();
    }

    private void initData() {
        mAdapter = new SchemeListAdapter(this,JumpDataManager.getInstance().getSchemes());
    }

    private void initUI() {
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(mAdapter);
        initListener();
    }

    private void initListener() {
        findViewById(R.id.btnUpdate).setOnClickListener(this);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnUpdate) {
            AppTools.getInstance().toast("start update");
            JumpDataManager.getInstance().refreshData(this, new JumpDataManager.refreshCallBack() {
                @Override
                public void finish(final boolean isSucceed) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isSucceed) {
                                mAdapter = new SchemeListAdapter(MainActivity.this, JumpDataManager.getInstance().getSchemes());
                                mListView.setAdapter(mAdapter);
                            }
                            AppTools.getInstance().toast(isSucceed? "update succeed":"update failure");
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        JumpScheme scheme = (JumpScheme) mAdapter.getItem(position);
        if (JumpTools.isVaildScheme(this,scheme)) {
            Intent intent = new Intent(this,SchemeActivity.class);
            intent.putExtra(SchemeActivity.EXTRA_SCHEME_ID,scheme.getUuid());
            startActivity(intent);
        } else {
            AppTools.getInstance().toast(getString(R.string.ivaildSchemeError));
        }
    }

}
