package com.bg.af.websocketdemo.service;

public interface NotificationDispatcherService {

    void dispatchMessage();

    boolean addSession(String session);

    boolean removeSession(String session);
}
