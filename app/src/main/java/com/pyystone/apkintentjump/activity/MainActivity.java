package com.pyystone.apkintentjump.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pyystone.apkintentjump.MyApp;
import com.pyystone.apkintentjump.R;
import com.pyystone.apkintentjump.data.JumpData;
import com.pyystone.apkintentjump.data.JumpDataManager;
import com.pyystone.apkintentjump.data.JumpScheme;
import com.pyystone.apkintentjump.ui.SchemeListAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView mListView;
    private SchemeListAdapter mAdapter;
    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new SchemeListAdapter(this,JumpDataManager.getInstance().getSchemes());
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
            JumpDataManager.getInstance().refreshData(this, new JumpDataManager.refreshCallBack() {
                @Override
                public void finish(boolean isSucceed) {

                }
            });
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        JumpScheme scheme = (JumpScheme) mAdapter.getItem(position);
        if (isVaildScheme(scheme)) {
            Intent intent = new Intent(this,SchemeActivity.class);
            intent.putExtra(SchemeActivity.EXTRA_SCHEME_UUID,scheme.getUuid());
            startActivity(intent);
        } else {
            if (mToast == null) {
                mToast = Toast.makeText(this,getString(R.string.ivaildSchemeError),Toast.LENGTH_SHORT);
            }
            mToast.cancel();
            mToast.show();
        }
    }

    private boolean isVaildScheme(JumpScheme scheme) {
//        Uri uri = Uri.parse(scheme.getScheme() + "://test");
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addCategory(Intent.CATEGORY_BROWSABLE);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        intent.setData(uri);
//        return getPackageManager().queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS).size() > 0;
        return true;
    }
}
