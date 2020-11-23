package com.harang.harang_socket.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageResponse {
    private Integer userId;
    private String userName;
    private String message;
    private boolean isMine;
}
