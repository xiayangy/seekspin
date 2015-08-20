package com.jarvis.mytaobao.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jarvis.http.CU_JSONResolve;
import com.jarvis.http.GetHttp;
import com.seek.spin.R;
import com.javis.Adapter.Adapter_ListView_ware;
import com.lesogo.cu.custom.xListview.XListView;
import com.lesogo.cu.custom.xListview.XListView.IXListViewListener;
import com.seek.spin.home.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

@SuppressLint("SimpleDateFormat")
public class WareActivity extends Activity implements OnTouchListener, IXListViewListener {
    private XListView listView;
    private LinearLayout ll_search;
    private ImageView iv_back;
    @SuppressWarnings("unused")
    private EditText ed_search;

    private AnimationSet animationSet;
    float fist_down_Y = 0;
    private int pageIndex = 0;
    private HashMap<String, Object> hashMap;
    private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ware_a);
        initView();
        new WareTask().execute();
    }

    private void initView() {

        ll_search = (LinearLayout) findViewById(R.id.ll_choice);
        ed_search = (EditText) findViewById(R.id.ed_Searchware);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        listView = (XListView) findViewById(R.id.listView_ware);
        listView.setOnTouchListener(this);
        listView.setXListViewListener(this);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                fist_down_Y = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (fist_down_Y - y > 250 && ll_search.isShown()) {
                    if (animationSet != null) {
                        animationSet = null;
                    }
                    animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.up_out);
                    ll_search.startAnimation(animationSet);
                    ll_search.setY(-100);
                    ll_search.setVisibility(View.GONE);
                }
                if (y - fist_down_Y > 250 && !ll_search.isShown()) {
                    if (animationSet != null) {
                        animationSet = null;
                    }
                    animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.down_in);
                    ll_search.startAnimation(animationSet);
                    ll_search.setY(0);
                    ll_search.setVisibility(View.VISIBLE);
                }
                break;

        }
        return false;

    }

    public void search(View view) {

        // 点击搜索跳转到指定的页面
        Intent intent = new Intent();
        intent.setClass(WareActivity.this, HomeActivity.class);
        startActivity(intent);


    }

    private class WareTask extends AsyncTask<Void, Void, HashMap<String, Object>> {

        ProgressDialog dialog = null;

        @Override
        protected void onPreExecute() {
            if (dialog == null) {
                dialog = ProgressDialog.show(WareActivity.this, "", "准备输入搜索");
                dialog.show();
            }


        }

        @Override
        protected HashMap<String, Object> doInBackground(Void... arg0) {
            String url = "";
            if (pageIndex == 0) {
                url = "http://192.168.0.111:3000/taoBaoQuery";
            } else {
                url = "http://192.168.0.111:3000/taoBaoQuery?pageIndex=" + pageIndex;
            }
            String json = GetHttp.RequstGetHttp(url);
            String[] LIST1_field = {"data"};

            String[] STR2_field = {"id", "name", "price", "sale_num", "address", "pic"};
            ArrayList<String[]> aL_STR2_field = new ArrayList<String[]>();
            aL_STR2_field.add(STR2_field);
            hashMap = CU_JSONResolve.parseHashMap2(json, null, LIST1_field, aL_STR2_field);
            return hashMap;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void onPostExecute(HashMap<String, Object> result) {

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }


            if (result != null && result.get("data") != null) {
                arrayList.addAll((Collection<? extends HashMap<String, Object>>) result.get("data"));
                listView.setAdapter(new Adapter_ListView_ware(WareActivity.this, arrayList));
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        Intent intent = new Intent(WareActivity.this, BabyActivity.class);
                        startActivity(intent);
                    }
                });

            } else {
                listView.setAdapter(new Adapter_ListView_ware(WareActivity.this));
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        Intent intent = new Intent(WareActivity.this, BabyActivity.class);
                        startActivity(intent);
                    }
                });
            }

            onLoad();

        }

    }

    @Override
    public void onRefresh() {
        pageIndex = 0;
        arrayList.clear();
        new WareTask().execute();
        onLoad();

    }

    @Override
    public void onLoadMore() {
        pageIndex += 1;
        if (pageIndex >= 4) {
            Toast.makeText(this, "�Ѿ����һҳ��", Toast.LENGTH_SHORT).show();
            onLoad();
            return;
        }
        new WareTask().execute();

    }

    private void onLoad() {
        listView.stopRefresh();
        listView.stopLoadMore();

        listView.setRefreshTime(getCurrentTime(System.currentTimeMillis()));
    }

    public static SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

    public static String getCurrentTime(long time) {
        if (0 == time) {
            return "";
        }

        return mDateFormat.format(new Date(time));

    }

}
