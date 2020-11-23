package com.harang.harang_socket.entitys.user.admin;

import com.harang.harang_socket.entitys.user.admin.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Integer> {
}
