package com.cgy.chengy.demofactory.websocket.notify;

import com.google.gson.annotations.SerializedName;

//对应数据bean
public class AnnounceMsgNotify {
    @SerializedName("msg_version")
    private String msgVersion;

    public String getMsgVersion() {
        return msgVersion;
    }

    public void setMsgVersion(String msgVersion) {
        this.msgVersion = msgVersion;
    }

}