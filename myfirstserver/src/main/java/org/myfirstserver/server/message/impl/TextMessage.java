package org.myfirstserver.server.message.impl;

import org.myfirstserver.server.message.Message;

public class TextMessage implements Message {

    private String content;

    public TextMessage(String content) {
        this.content = content;
    }

    @Override
    public String content() {
        return this.content;
    }
}