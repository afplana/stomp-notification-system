package com.bg.af.websocketdemo;

import java.time.OffsetDateTime;

public class Message {

    String text;
    OffsetDateTime time;

    public Message(String text, OffsetDateTime time) {
        this.text = text;
        this.time = time;
    }
}
