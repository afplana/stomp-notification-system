package com.bg.af.websocketdemo.service;

import com.bg.af.websocketdemo.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationDispatcherServiceImpl implements NotificationDispatcherService {

    private static final String EVENT_DESTINATION = "/notification/event";
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final SimpMessagingTemplate messagingTemplate;
    private final Set<String> sessions = new HashSet<>();

    @Override
    @Scheduled(fixedDelay = 5000)
    public void dispatchMessage() {
        sessions.forEach(session -> {
            log.info("Notification to: " + session);
            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
            headerAccessor.setSessionId(session);
            headerAccessor.setLeaveMutable(true);

            messagingTemplate
                    .convertAndSendToUser(
                            session,
                            EVENT_DESTINATION,
                            messageSupplier.get(),
                            headerAccessor.getMessageHeaders()
                    );
        });
    }

    @EventListener
    public boolean sessionDisconnectionHandler(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        log.info("Disconnecting " + sessionId + "!");
        return removeSession(sessionId);
    }

    public boolean addSession(String session) {
        return this.sessions.add(session);
    }

    public boolean removeSession(String session) {
        return this.sessions.remove(session);
    }

    // Simulate messages
    private final Supplier<Message> messageSupplier = () -> {
        int count = new  Random().nextInt(500);
        StringBuilder text = new StringBuilder();
        while (count-- != 0) {
            int index = new Random().nextInt(ALPHABET.length());
            text.append(ALPHABET.charAt(index));
        }
        return new Message(text.toString(), OffsetDateTime.now());
    };
}
