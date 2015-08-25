package com.seek.spin.message;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.seek.spin.R;
import com.seek.spin.listtable.MessageModel;
import com.seek.spin.listtable.Utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        initMessageList();
    }

    private void initMessageList() {

        //默认加载数据
        List<MessageModel> list = new ArrayList<MessageModel>();
        list.add(new MessageModel("测试消息标题", "测试消息内容", new Date(), "channel", "数据", "unreaded"));
        list.add(new MessageModel("测试消息标题", "测试消息内容", new Date(), "channel", "数据", "unreaded"));
        list.add(new MessageModel("测试消息标题", "测试消息内容", new Date(), "channel", "数据", "unreaded"));
        list.add(new MessageModel("测试消息标题", "测试消息内容", new Date(), "channel", "数据", "unreaded"));
        list.add(new MessageModel("测试消息标题", "测试消息内容", new Date(), "channel", "数据", "unreaded"));
        list.add(new MessageModel("测试消息标题", "测试消息内容", new Date(), "channel", "数据", "unreaded"));
        list.add(new MessageModel("测试消息标题", "测试消息内容", new Date(), "channel", "数据", "unreaded"));
        list.add(new MessageModel("测试消息标题", "测试消息内容", new Date(), "channel", "数据", "unreaded"));
        list.add(new MessageModel("测试消息标题", "测试消息内容", new Date(), "channel", "数据", "unreaded"));

        View view = LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.activity_message_list, null);

        ListView tableListView = (ListView) findViewById(R.id.message_list);
        MessageListAdapter messageListAdapter = new MessageListAdapter(getApplicationContext(), list);
        tableListView.setAdapter(messageListAdapter);
        Utility.setListViewHeightBasedOnChildren(tableListView);
    }
}
