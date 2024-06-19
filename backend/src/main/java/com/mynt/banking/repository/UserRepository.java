package com.mynt.banking.repository;

import org.springframework.stereotype.Repository;
import com.mynt.banking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
