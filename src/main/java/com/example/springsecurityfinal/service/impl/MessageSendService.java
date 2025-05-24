package com.example.springsecurityfinal.service.impl;

import com.example.springsecurityfinal.domain.message.MessagePayload;
import com.example.springsecurityfinal.repository.MessageSendClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSendService {
    private final MessageSendClient messageSendClient;

    public void sendBlockedMessage(String id) {
        String text = "'" + id + "' 님이 로그인 5회 실패로 60초간 차단되었습니다.";
        MessagePayload payload = new MessagePayload("알림봇", text);
        messageSendClient.sendMessage(payload);
    }
}
