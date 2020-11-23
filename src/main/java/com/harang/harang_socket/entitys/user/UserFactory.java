package com.harang.harang_socket.entitys.user;

import com.harang.harang_socket.entitys.user.admin.AdminRepository;
import com.harang.harang_socket.entitys.user.customer.Customer;
import com.harang.harang_socket.entitys.user.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class UserFactory {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;

    public User getUser(Integer id) throws Exception {
        Optional<Customer> student = customerRepository.findById(id);
        if (student.isPresent()) return student.get();
        else return adminRepository.findById(id)
                .orElseThrow(Exception::new);
    }

    public User getStudent(Integer id) throws Exception {
        return customerRepository.findById(id)
                .orElseThrow(Exception::new);
    }

    public User getAdmin(Integer id) throws Exception {
        return adminRepository.findById(id)
                .orElseThrow(Exception::new);
    }
}
