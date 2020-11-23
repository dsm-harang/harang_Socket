package com.harang.harang_socket.entitys.message.repository;

import com.harang.harang_socket.entitys.message.MessageRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRoomRepository extends CrudRepository<MessageRoom, Integer> {
}
