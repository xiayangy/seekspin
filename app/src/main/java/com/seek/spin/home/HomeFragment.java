package com.seek.spin.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jarvis.mytaobao.home.WareActivity;
import com.seek.spin.R;
import com.javis.Adapter.Adapter_GridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.seek.spin.customview.BannerModel;
import com.seek.spin.model.Person;
import com.seek.spin.listtable.TableAdapter;
import com.seek.spin.listtable.Utility;
import com.zxing.activity.CaptureActivity;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

public class HomeFragment extends Fragment {
    private TextView tv_top_title;
    private GridView gridView_classify;
    private GridView my_gridView_hot;
    private Adapter_GridView adapter_GridView_classify;
//    private Adapter_GridView_hot adapter_GridView_hot;
    //    private AbSlidingPlayView viewPager;
    private ImageView iv_shao;
    // 插件图标
    private int[] pic_path_classify = {R.drawable.menu_guide_1, R.drawable.menu_guide_2, R.drawable.menu_guide_3, R.drawable.menu_guide_4, R.drawable.menu_guide_5, R.drawable.menu_guide_6, R.drawable.menu_guide_7, R.drawable.menu_guide_8};
    private int[] pic_path_hot = {R.drawable.menu_1, R.drawable.menu_2, R.drawable.menu_3, R.drawable.menu_4, R.drawable.menu_5, R.drawable.menu_6};
    private ArrayList<View> allListView;
    private int[] resId = {R.drawable.show_m1, R.drawable.menu_viewpager_1, R.drawable.menu_viewpager_2, R.drawable.menu_viewpager_3, R.drawable.menu_viewpager_4, R.drawable.menu_viewpager_5};

    // 轮播组件
    private AsyncHttpClient mAsyncHttpClient;
    private BGABanner mDepthBanner;
    private List<View> mDepthViews;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_f, null);
        initView(view);
        mAsyncHttpClient = new AsyncHttpClient();
        initDepth(view);
        initInfoTable(view);

        return view;
    }

    private void initView(View view) {
        iv_shao = (ImageView) view.findViewById(R.id.iv_shao);
        iv_shao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivity(intent);
            }
        });
        tv_top_title = (TextView) view.findViewById(R.id.tv_top_title);
        tv_top_title.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), WareActivity.class);
                startActivity(intent);
            }
        });

        gridView_classify = (GridView) view.findViewById(R.id.my_gridview);
//        my_gridView_hot = (GridView) view.findViewById(R.id.my_gridview_hot);
        gridView_classify.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        my_gridView_hot.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter_GridView_classify = new Adapter_GridView(getActivity(), pic_path_classify);
//        adapter_GridView_hot = new Adapter_GridView_hot(getActivity(), pic_path_hot);
        gridView_classify.setAdapter(adapter_GridView_classify);
//        my_gridView_hot.setAdapter(adapter_GridView_hot);

//        viewPager = (AbSlidingPlayView) view.findViewById(R.id.viewPager_menu);
//        viewPager.setPlayType(1);
//        viewPager.setSleepTime(3000);

        gridView_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), WareActivity.class);
                startActivity(intent);
            }
        });
//        my_gridView_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getActivity(), BabyActivity.class);
//                startActivity(intent);
//            }
//        });

        //initViewPager();
    }

//    private void initViewPager() {
//
//        if (allListView != null) {
//            allListView.clear();
//            allListView = null;
//        }
//        allListView = new ArrayList<View>();
//
//        for (int i = 0; i < resId.length; i++) {
//            View view = LayoutInflater.from(getActivity()).inflate(R.layout.pic_item, null);
//            ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
//            imageView.setImageResource(resId[i]);
//            allListView.add(view);
//        }
//
//
//        viewPager.addViews(allListView);
//        viewPager.startPlay();
//        viewPager.setOnItemClickListener(new AbOnItemClickListener() {
//            @Override
//            public void onClick(int position) {
//                Intent intent = new Intent(getActivity(), BabyActivity.class);
//                startActivity(intent);
//            }
//        });
//    }


    // 初始化轮播组件
    private void initDepth(View view) {
        mDepthBanner = (BGABanner) view.findViewById(R.id.banner_main_depth);
        mDepthViews = getViews(6);
        mDepthBanner.setViews(mDepthViews);
        mAsyncHttpClient.get("https://raw.githubusercontent.com/bingoogolapple/BGABanner-Android/server/api/6item.json", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                BannerModel bannerModel = new Gson().fromJson(responseString, BannerModel.class);
                SimpleDraweeView simpleDraweeView;
                for (int i = 0; i < mDepthViews.size(); i++) {
                    simpleDraweeView = (SimpleDraweeView) mDepthViews.get(i);
                    simpleDraweeView.setImageURI(Uri.parse(bannerModel.imgs.get(i)));
                }
                mDepthBanner.setTips(bannerModel.tips);
            }
        });
    }

    private List<View> getViews(int count) {
        List<View> views = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            views.add(LayoutInflater.from(getActivity()).inflate(R.layout.view_image, null));
        }
        return views;
    }

    private void initInfoTable(View view) {

        ViewGroup tableTitle = (ViewGroup) view.findViewById(R.id.table_title);
        tableTitle.setBackgroundColor(Color.rgb(255, 100, 10));

        //默认加载数据
        List<Person> list = new ArrayList<Person>();
        list.add(new Person("xiayy", "man", 5));
        list.add(new Person("xiayy1", "man", 5));
        list.add(new Person("xiayy", "man", 5));
        list.add(new Person("xiayy1", "man", 5));
        list.add(new Person("xiayy", "man", 5));
        list.add(new Person("xiayy1", "man", 5));
        list.add(new Person("xiayy", "man", 5));
        list.add(new Person("xiayy1", "man", 5));

        ListView tableListView = (ListView) view.findViewById(R.id.list);
        TableAdapter adapter = new TableAdapter(this.getActivity(), list);
        tableListView.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(tableListView);

        //添加标题的点击事件
        TextView textName = (TextView) view.findViewById(R.id.text_name);
        textName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Person> list = new ArrayList<Person>();
                list.add(new Person("xiayy", "man", 500));
                list.add(new Person("xiayy1", "man", 5));
                list.add(new Person("xiayy", "man", 5));
                list.add(new Person("xiayy1", "man", 5));
                list.add(new Person("xiayy", "man", 5));
                list.add(new Person("xiayy1", "man", 5));
                list.add(new Person("xiayy", "man", 5));
                list.add(new Person("xiayy1", "man", 5));

                ListView tableListView = (ListView) getView().findViewById(R.id.list);
                TableAdapter adapter = new TableAdapter(getActivity(), list);
                tableListView.setAdapter(adapter);
                Utility.setListViewHeightBasedOnChildren(tableListView);

            }
        });
        TextView textSex = (TextView) view.findViewById(R.id.text_sex);
        textSex.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Person> list = new ArrayList<Person>();
                list.add(new Person("xiayy", "man", 50));
                list.add(new Person("xiayy1", "man", 5));
                list.add(new Person("xiayy", "man", 5));
                list.add(new Person("xiayy1", "man", 5));
                list.add(new Person("xiayy", "man", 5));
                list.add(new Person("xiayy1", "man", 5));
                list.add(new Person("xiayy", "man", 5));
                list.add(new Person("xiayy1", "man", 5));

                ListView tableListView = (ListView) getView().findViewById(R.id.list);
                TableAdapter adapter = new TableAdapter(getActivity(), list);
                tableListView.setAdapter(adapter);
                Utility.setListViewHeightBasedOnChildren(tableListView);

            }
        });

        TextView textAge = (TextView) view.findViewById(R.id.text_age);
        textAge.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Person> list = new ArrayList<Person>();
                list.add(new Person("xiayy", "man", 50));
                list.add(new Person("xiayy1", "man", 5));

                ListView tableListView = (ListView) getView().findViewById(R.id.list);
                TableAdapter adapter = new TableAdapter(getActivity(), list);
                tableListView.setAdapter(adapter);
                Utility.setListViewHeightBasedOnChildren(tableListView);

            }
        });

        // 初始化第二个表格
        ViewGroup tableTitle2 = (ViewGroup) view.findViewById(R.id.table2_title);
        tableTitle2.setBackgroundColor(Color.rgb(255, 100, 10));

        //默认加载数据
        List<Person> list2 = new ArrayList<Person>();
        list2.add(new Person("xiayy", "man", 5));
        list2.add(new Person("xiayy1", "man", 5));
        list2.add(new Person("xiayy", "man", 5));

        ListView tableListView2 = (ListView) view.findViewById(R.id.list2);
        TableAdapter adapter2 = new TableAdapter(this.getActivity(), list2);
        tableListView2.setAdapter(adapter2);
        Utility.setListViewHeightBasedOnChildren(tableListView2);


    }

}
