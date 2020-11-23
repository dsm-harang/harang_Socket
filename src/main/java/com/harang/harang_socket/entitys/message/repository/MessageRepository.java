package com.harang.harang_socket.entitys.message.repository;

import com.harang.harang_socket.entitys.message.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Integer> {
}
