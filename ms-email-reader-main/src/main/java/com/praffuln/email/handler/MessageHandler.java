package com.praffuln.email.handler;

import java.util.List;

import com.praffuln.email.wrapper.Message;

public interface MessageHandler {

    List<Message> getUnread();

    void markAsRead(List<Message> messages);
}
