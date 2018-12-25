package com.cgy.chengy.demofactory.websocket.notify;

//具体逻辑对应的处理子类
@NotifyClass(AnnounceMsgNotify.class)
public class AnnounceMsgListener implements INotifyListener<AnnounceMsgNotify> {

    @Override
    public void fire(AnnounceMsgNotify announceMsgNotify) {
        //这里处理具体的逻辑
    }
}