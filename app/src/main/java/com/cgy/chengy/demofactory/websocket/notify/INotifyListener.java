package com.cgy.chengy.demofactory.websocket.notify;

//抽象接口
public interface INotifyListener<T> {
    void fire(T t);
}