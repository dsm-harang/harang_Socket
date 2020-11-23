package com.harang.harang_socket.service.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.harang.harang_socket.payload.request.MessageRequest;

public interface SocketService {
    void connect(SocketIOClient client);
    void disConnect(SocketIOClient client);
    void joinRoom(SocketIOClient client, String room);
    void chat(SocketIOClient client, MessageRequest messageRequest);
}
