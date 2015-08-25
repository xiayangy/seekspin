package com.seek.spin.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seek.spin.R;
import com.seek.spin.listtable.MessageModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by xiayangyang on 15/8/20.
 */
public class MessageListAdapter extends BaseAdapter {

    private Context context;

    private List<MessageModel> messageList;
    public MessageListAdapter(Context context) {
        this.context = context;
    }

    public MessageListAdapter(Context context, List<MessageModel> list) {
        this.context = context;
        this.messageList = list;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int i) {
        return messageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MessageModel message = (MessageModel) this.getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.message_list_view, null);
            viewHolder.messageTitle = (TextView) convertView.findViewById(R.id.tv_title_msg_list_list_item);
            viewHolder.messageDate = (TextView) convertView.findViewById(R.id.tv_time_msg_list_list_item);
            viewHolder.messageContent = (TextView) convertView.findViewById(R.id.tv_content_msg_list_list_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.messageTitle.setText(message.getTitle());
        viewHolder.messageDate.setText(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(message.getDate()));
        viewHolder.messageContent.setText(message.getContent());

        return convertView;

    }

    public static class ViewHolder {
        public TextView messageTitle;
        public TextView messageDate;
        public TextView messageContent;

    }
}
