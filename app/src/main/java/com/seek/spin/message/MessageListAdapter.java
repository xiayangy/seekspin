package com.seek.spin.message;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seek.spin.listtable.BaseListViewAdapter;

/**
 * Created by xiayangyang on 15/8/20.
 */
public class MessageListAdapter extends BaseListViewAdapter {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return convertView;

    }

    public static class ViewHolder {
        public TextView mTextName;
        public TextView mTextSex;
        public TextView mTextAge;

    }
}
