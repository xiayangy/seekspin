package com.seek.spin.listtable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seek.spin.R;

/**
 * Created by xiayangyang on 15/8/18.
 */
public class ProductionListAdapter extends BaseAdapter {

    private Context context;

    public ProductionListAdapter(Context context) {
        this.context = context;
    }

    private String[] str = new String[]{"产品1", "产品2", "产品3","产品4","产品5","产品1", "产品2", "产品3","产品4","产品5","产品1", "产品2", "产品3","产品4","产品5"};


    @Override
    public int getCount() {
        return str.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View currentView, ViewGroup viewGroup) {

        HolderView holderView = null;
        if (currentView == null) {
            holderView = new HolderView();
            currentView = LayoutInflater.from(context).inflate(R.layout.adapter_production_list, null);
            holderView.nameText = (TextView) currentView.findViewById(R.id.productionName);
            holderView.opText = (TextView) currentView.findViewById(R.id.op);

            currentView.setTag(holderView);
        } else {
            holderView = (HolderView) currentView.getTag();
        }

        holderView.nameText.setText(str[position]);
        holderView.opText.setText("操作");
        if(position%2==0){
        }

        return currentView;
    }


    public class HolderView {
        private TextView nameText;
        private TextView opText;
    }
}
