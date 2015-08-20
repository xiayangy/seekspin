package com.seek.spin.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jarvis.mytaobao.home.BabyActivity;
import com.jarvis.mytaobao.home.WareActivity;
import com.seek.spin.R;
import com.seek.spin.listtable.ProductionListAdapter;

/**
 * Created by xiayangyang on 15/8/18.
 */
public class PublishGoods extends Fragment {

    private ListView listView_tao;
    private TextView tv_top_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tao_f, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        listView_tao = (ListView) view.findViewById(R.id.listView_tao);
        listView_tao.setAdapter(new ProductionListAdapter(getActivity()));
        listView_tao.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(getActivity(), BabyActivity.class);
                startActivity(intent);
            }
        });

        tv_top_title = (TextView) view.findViewById(R.id.tv_top_title);
        tv_top_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), WareActivity.class);
                startActivity(intent);
            }
        });

    }
}
