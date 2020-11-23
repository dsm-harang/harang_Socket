package com.harang.harang_socket.service.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.harang.harang_socket.entitys.message.Message;
import com.harang.harang_socket.entitys.message.repository.MessageRepository;
import com.harang.harang_socket.entitys.message.repository.MessageRoomRepository;
import com.harang.harang_socket.entitys.user.User;
import com.harang.harang_socket.entitys.user.customer.CustomerRepository;
import com.harang.harang_socket.payload.request.ErrorResponse;
import com.harang.harang_socket.payload.request.MessageRequest;
import com.harang.harang_socket.payload.response.MessageResponse;
import com.harang.harang_socket.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class SocketServiceImpl implements SocketService{

    private final SocketIOServer socketIOServer;

    private final JwtTokenProvider jwtTokenProvider;

    private final MessageRepository messageRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void connect(SocketIOClient client) {
        String token = client.getHandshakeData().getSingleUrlParam("token");
        if (!jwtTokenProvider.validateToken(token)) {
            clientDisconnect(client, 403, "Can not resolve token");
            return;
        }

        User user;
        try {
            user = customerRepository.findById(Integer.valueOf(jwtTokenProvider.getUserId(token)))
                    .orElseThrow(RuntimeException::new);
            client.set("user", user);
        } catch (Exception e) {
            clientDisconnect(client, 404, "Could not get user");
            return;
        }
    }

    @Override
    public void disConnect(SocketIOClient client) {
        printLog(
                client,
                String.format("Socket Disconnected, Session Id: %s%n", client.getSessionId()));
    }

    @Override
    public void joinRoom(SocketIOClient client, String room) {
        User user = client.get("user");
        Integer targetId;

        if (user == null) {
            clientDisconnect(client, 403, "Invalid Connection");
            return;
        }

        boolean existUser = messageRepository.existsByRoomIdAndSenderId(room, user.getId());
        if (!existUser) {
            clientDisconnect(client, 401, "Invalid Room");
            return;
        }

        try {
            targetId = messageRepository.findByRoomIdAndSenderIdNot(room, user.getId())
                    .orElseThrow(RuntimeException::new).getSenderId();
        } catch (Exception e) {
            clientDisconnect(client, 404, "Target Not found");
            return;
        }

        printLog(
                client,
                String.format("Join Room [senderId(%d) -> receiverId(%d)] Session Id: %s%n",
                        user.getId(), targetId, client.getSessionId())
        );

    }

    @Override
    public void chat(SocketIOClient client, MessageRequest messageRequest) {
        if (!client.getAllRooms().contains(messageRequest.getRoomId())) {
            clientDisconnect(client, 401, "Invalid Connection");
            return;
        }

        User user = client.get("user");
        Message message = messageRepository.save(
                Message.builder()
                    .senderId(user.getId())
                    .roomId(messageRequest.getRoomId())
                    .content(messageRequest.getRoomId())
                    .build()
        );

        socketIOServer.getRoomOperations(messageRequest.getRoomId()).sendEvent(
                "send",
                client,
                MessageResponse.builder()
                    .userId(user.getId())
                    .userName(user.getName())
                    .message(messageRequest.getMessage())
                    .isMine(false)
                    .build()
        );

    }
    private void printLog(SocketIOClient client, String content) {
        String stringDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        System.out.printf(
                "%s  %s - [%s] - %s",
                stringDate,
                "SOCKET",
                client.getRemoteAddress().toString().substring(1),
                content
        );
    }

    private void clientDisconnect(SocketIOClient client, Integer status, String reason) {
        client.sendEvent("error", new ErrorResponse(status, reason));
        client.disconnect();
        printLog(
                client,
                String.format("Socket Disconnected, Session Id: %s - %s%n", client.getSessionId(), reason)
        );
    }

}
