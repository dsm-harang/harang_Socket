package com.harang.harang_socket.payload.request;

import lombok.Getter;

@Getter
public class MessageRequest {
    private String roomId;
    private String message;
}
