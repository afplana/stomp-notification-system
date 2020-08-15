package com.bg.af.websocketdemo.controller;

import com.bg.af.websocketdemo.Message;
import com.bg.af.websocketdemo.service.NotificationDispatcherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.OffsetDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationDispatcherService dispatcherService;

    @SendTo("/topic/events")
    @MessageMapping("/notifications")
    public Message send( ) {
        return new Message("message.text", OffsetDateTime.now());
    }

    @MessageMapping("/start")
    public void start(StompHeaderAccessor stompHeaderAccessor) {
        String session = stompHeaderAccessor.getSessionId();
        if (!dispatcherService.addSession(session)) {
            throw new IllegalStateException("Session: " + session + " could not be established.");
        }
        log.info("Session: " +session+ "successfully registered");
    }

    @MessageMapping("/stop")
    public void stop(StompHeaderAccessor stompHeaderAccessor) {
        String session = stompHeaderAccessor.getSessionId();
        if (!dispatcherService.removeSession(session)) {
            throw new IllegalStateException("Session: " + session + " could not be stopped.");
        }
        log.info("Session: " +session+ "successfully removed");
    }
}
