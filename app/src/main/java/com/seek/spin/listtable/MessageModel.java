package com.seek.spin.listtable;

import java.util.Date;

/**
 * Created by xiayangyang on 15/8/20.
 */
public class MessageModel {

    private String title;//标题
    private String content;//内容
    private Date date;//事件
    private String channelId;//通道Id
    private String extraInfo;//其他信息
    private String readState;//阅读状态（已读 or 未读）

    public String getReadState() {
        return readState;
    }

    public void setReadState(String readState) {
        this.readState = readState;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
