package com.duke.repos;

import com.duke.entity.mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepos extends JpaRepository<User,Integer> {

    User findFirstByPhone(String phone);
}
