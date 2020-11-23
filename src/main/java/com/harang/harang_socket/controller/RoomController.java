package com.harang.harang_socket.controller;

import com.harang.harang_socket.service.room.MessageRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final MessageRoomService messageRoomService;

    @PostMapping("/{id}")
    public void createMessageRoom(@PathVariable Integer userId) {
        messageRoomService.createMessageRoom(userId);
    }
}
