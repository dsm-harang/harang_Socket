package com.harang.harang_socket.entitys.message.repository;

import com.harang.harang_socket.entitys.message.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message, Integer> {
    boolean existsByRoomIdAndSenderId(String roomId, Integer senderId);
    Optional<Message> findByRoomIdAndSenderIdNot(String roomId, Integer senderId);
}
