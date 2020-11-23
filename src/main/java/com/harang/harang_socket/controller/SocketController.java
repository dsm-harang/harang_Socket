package com.harang.harang_socket.controller;

import com.corundumstudio.socketio.SocketIOServer;
import com.harang.harang_socket.payload.request.MessageRequest;
import com.harang.harang_socket.service.socket.SocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class SocketController {

    private final SocketIOServer server;

    private final SocketService socketService;

    @PostConstruct
    public void setSocketMapping() {
        server.addConnectListener(socketService::connect);
        server.addDisconnectListener(socketService::disConnect);

        server.addEventListener("joinRoom", String.class,
                (client, data, ackSender) -> socketService.joinRoom(client, data));

        server.addEventListener("send", MessageRequest.class,
                (client, data, ackSender) -> socketService.chat(client, data));
    }

}
