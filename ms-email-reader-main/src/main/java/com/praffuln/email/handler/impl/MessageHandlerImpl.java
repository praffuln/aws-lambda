package com.praffuln.email.handler.impl;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.graph.requests.MessageCollectionPage;
import com.praffuln.email.Logger;
import com.praffuln.email.authentication.AccessProvider;
import com.praffuln.email.handler.MessageHandler;
import com.praffuln.email.wrapper.Message;
import com.praffuln.email.wrapper.impl.MessageImpl;

public class MessageHandlerImpl implements MessageHandler {

    private AccessProvider accessProvider;

    public MessageHandlerImpl(final AccessProvider accessProvider) {
        this.accessProvider = accessProvider;
    }

    @Override
    public List<Message> getUnread() {

        final List<Message> unread = new ArrayList<>();

        final MessageCollectionPage page = 
            accessProvider
            .getServiceClient()
            .users()
            .byId("prafful.namdev18@wql6q.onmicrosoft.com")   
            .messages()
            .buildRequest()
            .filter("isRead eq false")
            .get();
        List<com.microsoft.graph.models.Message> messages = 
            page.getCurrentPage();

        int pageCount = 1;
        while (messages != null && messages.size() > 0) {
            for (com.microsoft.graph.models.Message message : messages) {
            	System.out.println("message.subject : " + message.subject + "    " + message.sender.emailAddress.address);
                if (!message.isRead) {
                    unread.add(new MessageImpl(accessProvider, message));
                }
            }
            Logger.logInfo("Processed unread page " + pageCount);
            messages =  page.getNextPage() != null ? page.getNextPage().buildRequest().get().getCurrentPage() : null;
            pageCount++;
        }

        return unread;
    }

    @Override
    public void markAsRead(final List<Message> messages) {

        com.microsoft.graph.models.Message msg = 
                new com.microsoft.graph.models.Message();
        msg.isRead = true;

        messages.forEach(message -> {
            com.microsoft.graph.models.Message response = 
                accessProvider
                .getServiceClient()
                .users()
                .byId("prafful.namdev18@wql6q.onmicrosoft.com") 
                .messages(message.getId())
                .buildRequest()
                .select("IsRead")
                .patch(msg);

            if (!response.isRead) {
                Logger.logError("Unable to successfully mark message " + 
                    message.getId() + " as 'read'");
            }
        });
    }

}
