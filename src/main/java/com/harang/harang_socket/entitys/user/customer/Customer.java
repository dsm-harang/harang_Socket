package com.harang.harang_socket.entitys.user.customer;

import com.harang.harang_socket.entitys.user.User;
import com.harang.harang_socket.security.AuthorityType;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String userId;

    private String password;

    private String name;

    private Integer age;

    private Integer phoneNumber;

    private String imagePath;

    private String intro;

    public AuthorityType getType() {
        return AuthorityType.USER;
    }

    public Customer updateFileName(String fileName) {
        this.imagePath = fileName;

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(userId, customer.userId) &&
                Objects.equals(password, customer.password) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(age, customer.age) &&
                Objects.equals(phoneNumber, customer.phoneNumber) &&
                Objects.equals(imagePath, customer.imagePath) &&
                Objects.equals(intro, customer.intro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, password, name, age, phoneNumber, imagePath, intro);
    }

}
