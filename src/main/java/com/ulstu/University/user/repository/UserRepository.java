package com.ulstu.University.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ulstu.University.user.model.User;

public interface UserRepository extends JpaRepository<User, Long>  {
    User findOneByLoginIgnoreCase(String login);
}
