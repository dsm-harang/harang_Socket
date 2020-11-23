package com.harang.harang_socket.service.room;

import com.harang.harang_socket.entitys.message.MessageRoom;
import com.harang.harang_socket.entitys.message.repository.MessageRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageRoomServiceImpl implements MessageRoomService{

    private final MessageRoomRepository messageRoomRepository;

    @Override
    public void createMessageRoom(Integer userId) {
        String roomId = UUID.randomUUID().toString();
        MessageRoom.builder()
                .roomId(roomId)
                .userId(userId)
                .build();
    }

}
