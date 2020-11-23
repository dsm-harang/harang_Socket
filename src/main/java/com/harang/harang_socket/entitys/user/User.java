package com.harang.harang_socket.entitys.user;

import com.harang.harang_socket.security.AuthorityType;

public interface User {
    Integer getId();
    String getUserId();
    String getPassword();
    String getName();
    Integer getAge();
    Integer getPhoneNumber();
    String getImagePath();
    String getIntro();
    AuthorityType getType();
}
