package com.seek.spin.listtable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by xiayangyang on 15/8/19.
 * 作为适配listView的基类，只需实现getView方法进行适配即可
 */
public class BaseListViewAdapter extends BaseAdapter {

    private List<?> list;
    private LayoutInflater inflater;

    private Context context;

    public BaseListViewAdapter(Context context, List<?> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public BaseListViewAdapter(Context context) {
        this.context = context;
    }

    public BaseListViewAdapter() {

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        Person person = (Person) this.getItem(position);
//        Log.d("xiayyy:", String.valueOf(position));
//        ViewHolder viewHolder;
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//            convertView = inflater.inflate(R.layout.list_item, null);
//            viewHolder.mTextName = (TextView) convertView.findViewById(R.id.text_name);
//            viewHolder.mTextSex = (TextView) convertView.findViewById(R.id.text_sex);
//            viewHolder.mTextAge = (TextView) convertView.findViewById(R.id.text_age);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        viewHolder.mTextName.setText(person.getName());
//        viewHolder.mTextSex.setText(person.getSex());
//        viewHolder.mTextAge.setText(person.getAge() + "岁");

        return convertView;
    }

//    public static class ViewHolder {
//        public TextView mTextName;
//        public TextView mTextSex;
//        public TextView mTextAge;
//
//    }
}
