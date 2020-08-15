package com.bg.af.websocketdemo;

import lombok.Value;

import java.io.Serializable;

@Value
public class Message implements Serializable {

    String text;

    public Message(String text) {
        this.text = text;
    }
}
