package com.example.spring_boot_study_hub.web_socket.Dto;

import com.example.spring_boot_study_hub.web_socket.Utils.Enums.MessageType;
import lombok.Builder;


@Builder
public class ChatMessage {
    private String content;
    private String sender;
    private MessageType messageType;

    public ChatMessage() {
    }

    public ChatMessage(String content, String sender, MessageType messageType) {
        this.content = content;
        this.sender = sender;
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
