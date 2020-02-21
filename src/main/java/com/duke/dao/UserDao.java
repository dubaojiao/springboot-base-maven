package com.duke.dao;

import com.duke.component.DaoComponent;
import com.duke.entity.mysql.User;
import com.duke.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDao {

    private final UserRepos userRepos;
    private final DaoComponent daoComponent;

    @Autowired
    public UserDao(UserRepos userRepos,DaoComponent daoComponent){
        this.userRepos  = userRepos;
        this.daoComponent = daoComponent;
    }


    public User findByPhone(String phone){
        return userRepos.findFirstByPhone(phone);
    }

    public void deleteAll() {
        userRepos.deleteAll();
    }

    public User save(User user) {
        return userRepos.save(user);
    }
}
